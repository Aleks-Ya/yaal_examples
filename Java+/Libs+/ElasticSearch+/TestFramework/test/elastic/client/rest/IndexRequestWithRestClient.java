package elastic.client.rest;

import org.apache.lucene.util.StringHelper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.test.ESIntegTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;
import util.InputStreamUtil;

import java.io.IOException;
import java.util.Random;

import static elastic.EsHelper.createRandomIndexName;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IndexRequestWithRestClient extends ESIntegTestCase {

    @Test
    @Ignore
    public void createIndex() throws IOException {
        var client = createRestClient();
        var indexName = new Random().nextInt(Integer.MAX_VALUE);
        var request = new Request("PUT", "/" + indexName);
        var response = client.performRequest(request);
        client.close();
        MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), CoreMatchers.equalTo(200));

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        MatcherAssert.assertThat(contentStr, containsString("\"acknowledged\":true"));
    }
}
