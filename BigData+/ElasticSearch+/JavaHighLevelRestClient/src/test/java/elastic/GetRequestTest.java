package elastic;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetRequestTest {
    @Test
    public void getDocumentById() throws IOException {
        RestClient lowLevelRestClient = ConnectionHelper.getLowLevelRestClient();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        String index = "people";
        GetRequest getRequest = new GetRequest(index, "companies", "1");
        GetResponse response = client.get(getRequest);

        System.out.println(response);
        assertThat(response.getIndex(), equalTo(index));
        assertThat(response.getSource().get("title"), equalTo("Oracle"));
    }

}
