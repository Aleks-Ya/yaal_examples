package elastic;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetRequestTest {
    @Test
    public void getDocumentById() throws IOException {
        var client = EsHelper.getHighLevelRestClient();

        var index = "people";
        var getRequest = new GetRequest(index, "companies", "1");
        var response = client.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(response);
        assertThat(response.getIndex(), equalTo(index));
        assertThat(response.getSource().get("title"), equalTo("Oracle"));
    }

}
