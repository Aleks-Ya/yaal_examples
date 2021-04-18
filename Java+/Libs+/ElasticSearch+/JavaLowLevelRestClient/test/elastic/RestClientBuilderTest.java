package elastic;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RestClientBuilderTest {

    @Test
    public void setHeaders() throws IOException {
        var header = new BasicHeader("header", "value");
        var defaultHeaders = new Header[]{header};
        var client = SecurityHelper.newRestClientBuilder()
                .setDefaultHeaders(defaultHeaders)
                .build();
        client.close();
    }

}
