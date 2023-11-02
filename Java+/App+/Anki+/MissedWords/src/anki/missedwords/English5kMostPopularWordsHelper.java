package anki.missedwords;

import anki.connect.AnkiConnect;
import anki.connect.AnkiField;
import org.apache.commons.csv.CSVFormat;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static anki.missedwords.Constants.ENGLISH_FIELD;
import static anki.missedwords.Constants.PART_OF_SPEECH_HEADER;
import static anki.missedwords.Constants.WORD_HEADER;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static util.ResourceUtil.resourceToFile;
import static util.ResourceUtil.resourceToPath;

public class English5kMostPopularWordsHelper {
    public static List<Word> readWordsFromResource() {
        try {
            var words5kFile = resourceToFile(English5kMostPopularWordsHelper.class, "english_5000_popular_words.csv");
            var stopWords = readAllLines(resourceToPath(English5kMostPopularWordsHelper.class, "stop_words.txt"));
            var format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
            try (var reader = new FileReader(words5kFile);
                 var parser = format.parse(reader)) {
                return parser.stream()
                        .map(record -> new Word(record.get(WORD_HEADER), record.get(PART_OF_SPEECH_HEADER)))
                        .distinct()
                        .filter(word -> !stopWords.contains(word.word()))
                        .collect(toList());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Word> getMissingUnknownWords() throws IOException {
        var english5kWords = readWordsFromResource();
        var stopWords = readAllLines(resourceToPath(English5kMostPopularWordsHelper.class, "stop_words.txt"));
        var knownWords1k = readAllLines(resourceToPath(English5kMostPopularWordsHelper.class, "known_words_from_1000.txt"));
        var knownWords5k = readAllLines(resourceToPath(English5kMostPopularWordsHelper.class, "known_words_from_5000.txt"));

        var ankiConnect = new AnkiConnect();
        var noteIds = ankiConnect.findNotes();
        var notes = ankiConnect.notesInfo(noteIds);
        var ankiEnglishWords = notes.stream()
                .map(note -> note.fields().get(ENGLISH_FIELD))
                .filter(Objects::nonNull)
                .map(AnkiField::value)
                .map(word -> {
                    var wordNoPrep = word;
                    wordNoPrep = wordNoPrep.startsWith("to ") ? wordNoPrep.substring(3) : wordNoPrep;
                    wordNoPrep = wordNoPrep.startsWith("a ") ? wordNoPrep.substring(2) : wordNoPrep;
                    wordNoPrep = wordNoPrep.startsWith("an ") ? wordNoPrep.substring(3) : wordNoPrep;
                    return wordNoPrep;
                })
                .toList();

        return english5kWords.stream()
                .filter(word -> !stopWords.contains(word.word()))
                .filter(word -> !knownWords1k.contains(word.word()))
                .filter(word -> !knownWords5k.contains(word.word()))
                .filter(word -> !ankiEnglishWords.contains(word.word()))
                .collect(toSet());
    }

    public static String partOfSpeachToTag(String partOfSpeach) {
        return switch (partOfSpeach) {
            case "v" -> "en::parts::verb";
            case "n" -> "en::parts::noun";
            case "j" -> "en::parts::adjective";
            case "c" -> "en::parts::conjunction";
            case "i" -> "en::parts::preposition";
            case "p" -> "en::parts::pronoun";
            case "d" -> "en::parts::determiner";
            case "a" -> "en::parts::article";
            case "m" -> "en::parts::number";
            case "r" -> "en::parts::adverb";
            case null, default -> throw new IllegalStateException("Unknown Part Of Speech code: " + partOfSpeach);
        };
    }
}

