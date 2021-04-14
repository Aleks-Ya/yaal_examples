package elastic.index;

import elastic.ConnectionHelper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateIndexRequestTest {
    private static final RestHighLevelClient client = ConnectionHelper.getHighLevelRestClient();

    @Test
    public void createAndDeleteIndex() throws IOException {
        String indexName = String.format("%s-%d", getClass().getSimpleName(), (new Random().nextInt(Integer.MAX_VALUE))).toLowerCase();
        assertFalse(isIndexExist(indexName));
        createIndex(indexName);
        assertTrue(isIndexExist(indexName));
        deleteIndex(indexName);
        assertFalse(isIndexExist(indexName));
    }

    private void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        assertTrue(acknowledgedResponse.isAcknowledged());
    }

    private void createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        assertTrue(createIndexResponse.isAcknowledged());
    }

    private boolean isIndexExist(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }
}
