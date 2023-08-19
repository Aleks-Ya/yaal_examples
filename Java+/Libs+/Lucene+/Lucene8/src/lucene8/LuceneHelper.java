package lucene8;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneHelper {
    public static List<String> directoryToTermList(Directory directory, String fieldName) {
        try {
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
            return actTermList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
