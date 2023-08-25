package lucene9.indexing.reader;

import lucene9.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GetTermFrequencyTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        try (var assistant = IndexAssistant.create()
                .addDoc(fieldName, List.of("John eats a cake.", "Mary eats a mango."))) {
            var directory = assistant.getDirectory();
            var tfMap = new HashMap<String, Long>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            tfMap.put(term.utf8ToString(), termsEnum.totalTermFreq());
                        }
                    }
                }
            }
            assertThat(tfMap).containsExactlyInAnyOrderEntriesOf(Map.of(
                    "john", 1L,
                    "eats", 2L,
                    "mango", 1L,
                    "mary", 1L,
                    "cake", 1L,
                    "a", 2L
            ));
        }
    }
}
