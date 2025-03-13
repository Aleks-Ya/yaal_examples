package lucene10.indexing.reader;

import lucene10.IndexAssistant;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IterateAllTermsTest {
    @Test
    void test() throws IOException {
        var fieldName = "text";
        try (var assistant = IndexAssistant.create().addDoc(fieldName, List.of("John drinks water.", "Mary eats a cake."))) {
            var directory = assistant.getDirectory();
            var actTermList = new ArrayList<String>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            actTermList.add(term.utf8ToString());
                        }
                    }
                }
            }
            assertThat(actTermList).containsExactlyInAnyOrder(
                    "john", "drinks", "water", "mary", "eats", "a", "cake");
        }
    }
}
