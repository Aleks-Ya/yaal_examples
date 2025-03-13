package lucene10;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHelper {
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

    public static Map<String, List<String>> directoryToTermToPayloadsMap(Directory directory, String fieldName) {
        try {
            var termPayloadMap = new HashMap<String, List<String>>();
            try (var reader = DirectoryReader.open(directory)) {
                for (var context : reader.leaves()) {
                    var leafReader = context.reader();
                    var terms = leafReader.terms(fieldName);
                    if (terms != null) {
                        var termsEnum = terms.iterator();
                        BytesRef term;
                        while ((term = termsEnum.next()) != null) {
                            var postingsEnum = termsEnum.postings(null, PostingsEnum.PAYLOADS);
                            while (postingsEnum.nextDoc() != PostingsEnum.NO_MORE_DOCS) {
                                var freq = postingsEnum.freq();
                                for (var i = 0; i < freq; i++) {
                                    postingsEnum.nextPosition();
                                    var payload = postingsEnum.getPayload();
                                    if (payload != null) {
                                        var payloadStr = payload.utf8ToString();
                                        var termStr = term.utf8ToString();
                                        var payloadList = termPayloadMap.getOrDefault(termStr, new ArrayList<>());
                                        payloadList.add(payloadStr);
                                        termPayloadMap.put(termStr, payloadList);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return termPayloadMap;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Document> topDocsToDocuments(TopDocs topDocs, IndexSearcher indexSearcher) {
        try {
            var result = new ArrayList<Document>();
            for (var scoreDoc : topDocs.scoreDocs) {
                var docId = scoreDoc.doc;
                var actDoc = indexSearcher.storedFields().document(docId);
                result.add(actDoc);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Integer, Document> topDocsToIdToDocumentMap(TopDocs topDocs, IndexSearcher indexSearcher) {
        try {
            var result = new HashMap<Integer, Document>();
            for (var scoreDoc : topDocs.scoreDocs) {
                var docId = scoreDoc.doc;
                var actDoc = indexSearcher.storedFields().document(docId);
                result.put(docId, actDoc);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
