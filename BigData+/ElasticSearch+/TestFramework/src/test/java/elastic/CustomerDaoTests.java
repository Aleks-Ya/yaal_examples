package elastic;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;

/**
 * fix it
 */
public class CustomerDaoTests extends ESIntegTestCase {

    @Test
    public void name() {
        String index = "test";
        createIndex(index);
        Client client = client();

        IndexAction indexAction = IndexAction.INSTANCE;
        IndexRequestBuilder indexRequestBuilder = new IndexRequestBuilder(client, indexAction);
        indexRequestBuilder.setIndex(index);
        indexRequestBuilder.setSource("{ \"a\": 1}");

        IndexRequest request = indexRequestBuilder.request();

        ActionFuture<IndexResponse> responseActionFuture = client.index(request);
        IndexResponse indexResponse = responseActionFuture.actionGet();
        RestStatus status = indexResponse.status();
        System.out.println(status);
    }
}
