package subtitles.subrip;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SubRipHelper {
    private static final String COUNTER_FIELD = "counter";
    private static final String START_FIELD = "start";
    private static final String END_FIELD = "end";
    public static final String TEXT_FIELD = "text";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");
    static final List<String> STOP_WORDS = List.of(
            "a", "an", "and", "are", "as", "at", "be", "but", "by",
            "for", "if", "in", "into", "is", "it",
            "no", "not", "of", "on", "or", "such",
            "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with"
    );

    public static void indexSubRipFile(Path subRipPath, Path indexDir) throws IOException {
        var srt = Files.readString(subRipPath, StandardCharsets.UTF_8).replaceFirst("\uFEFF", "");
        var records = srt.split("([\n\r]\n){2}");
        var docs = Arrays.stream(records).map(record -> {
            var rows = record.split("[\n\r]\n");
            var counterRow = rows[0];
            var timeRow = rows[1];
            var textRow = String.join(" ", Arrays.copyOfRange(rows, 2, rows.length));

            var timeRowSplit = timeRow.split(" --> ");
            var startTime = LocalTime.parse(timeRowSplit[0], timeFormatter);
            var endTime = LocalTime.parse(timeRowSplit[1], timeFormatter);

            var doc = new Document();
            doc.add(new NumericDocValuesField(COUNTER_FIELD, Long.parseLong(counterRow)));
            doc.add(new NumericDocValuesField(START_FIELD, startTime.toNanoOfDay()));
            doc.add(new NumericDocValuesField(END_FIELD, endTime.toNanoOfDay()));
            doc.add(new TextField(TEXT_FIELD, textRow, Field.Store.YES));
            return doc;
        }).toList();

        try (var directory = new MMapDirectory(indexDir);
             var analyzer = new ExcludeNumbersAnalyzer(new CharArraySet(STOP_WORDS, true))) {
            var config = new IndexWriterConfig(analyzer);
            try (var writer = new IndexWriter(directory, config)) {
                for (var doc : docs) {
                    writer.addDocument(doc);
                }
            }
        }
    }

    public static List<TermData> readTermList(Path indexDir) {
        try (var directory = new MMapDirectory(indexDir)) {
            var termList = new ArrayList<TermData>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(SubRipHelper.TEXT_FIELD);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            var termText = term.utf8ToString();
                            var termFrequency = termsEnum.totalTermFreq();
                            termList.add(new TermData(termText, termFrequency));
                        }
                    }
                }
            }
            return termList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
