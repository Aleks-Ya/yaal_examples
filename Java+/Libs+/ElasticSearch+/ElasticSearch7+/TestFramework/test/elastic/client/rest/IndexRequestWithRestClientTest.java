package elastic.client.rest;

import org.assertj.core.api.Assertions;
import org.elasticsearch.client.Request;
import org.elasticsearch.test.ESIntegTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;
import util.InputStreamUtil;
import util.RandomUtil;

import java.io.IOException;

class IndexRequestWithRestClientTest extends ESIntegTestCase {

    @Test
    @Ignore
    void createIndex() throws IOException {
        var client = createRestClient();
        var indexName = RandomUtil.randomIntPositive();
        var request = new Request("PUT", "/" + indexName);
        var response = client.performRequest(request);
        client.close();
        MatcherAssert.assertThat(response.getStatusLine().getStatusCode(), CoreMatchers.equalTo(200));

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        Assertions.assertThat(contentStr).containsSubsequence("\"acknowledged\":true");
    }
}
