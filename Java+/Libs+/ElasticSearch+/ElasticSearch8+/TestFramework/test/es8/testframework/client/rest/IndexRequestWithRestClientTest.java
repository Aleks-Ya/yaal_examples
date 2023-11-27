package es8.testframework.client.rest;

import org.assertj.core.api.Assertions;
import org.elasticsearch.client.Request;
import org.elasticsearch.test.ESIntegTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;
import util.InputStreamUtil;

import java.io.IOException;

public class IndexRequestWithRestClientTest extends ESIntegTestCase {
    @Test
    @Ignore("Does not work")
    public void createIndex() throws IOException {
        var client = createRestClient();
        var request = new Request("GET", "/");
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
