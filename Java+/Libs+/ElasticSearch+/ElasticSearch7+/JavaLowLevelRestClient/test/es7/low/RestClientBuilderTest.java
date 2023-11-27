package es7.low;

import es7.low.SecurityHelper;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RestClientBuilderTest {

    @Test
    void setHeaders() throws IOException {
        var header = new BasicHeader("header", "value");
        var defaultHeaders = new Header[]{header};
        var client = SecurityHelper.newRestClientBuilder()
                .setDefaultHeaders(defaultHeaders)
                .build();
        client.close();
    }

}
