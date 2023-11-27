package es7.high;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class GetRequestTest {
    @Test
    void getDocumentById() throws IOException {
        var client = EsHelper.getHighLevelRestClient();

        var index = "people";
        var getRequest = new GetRequest(index, "companies", "1");
        var response = client.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(response);
        assertThat(response.getIndex()).isEqualTo(index);
        assertThat(response.getSource().get("title")).isEqualTo("Oracle");
    }

}
