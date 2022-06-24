package request;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class GetRequestTest {
    @Test
    void get() throws IOException {
        try (var httpClient = HttpClients.createDefault()) {
            var httpGet = new HttpGet("http://httpbin.org/get");
            try (var response = httpClient.execute(httpGet)) {
                assertThat(response.getCode()).isEqualTo(200);
            }
        }
    }

}