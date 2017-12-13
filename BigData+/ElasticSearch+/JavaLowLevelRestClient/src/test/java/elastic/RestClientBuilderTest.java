package elastic;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;

public class RestClientBuilderTest {

    @Test
    public void setHeaders() throws IOException {
        BasicHeader header = new BasicHeader("header", "value");
        Header[] defaultHeaders = new Header[]{header};
        RestClient client = SecurityHelper.newRestClientBuilder()
                .setDefaultHeaders(defaultHeaders)
                .build();
        client.close();
    }

}
