package feign;

import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringBodyTest {

    interface Server {
        @RequestLine("GET /string")
        String string();
    }

    @Test
    public void body() {
        var expBody = "abc";

        var mockClient = new MockClient().ok(HttpMethod.GET, "/string", expBody);
        var server = Feign.builder().client(mockClient).target(new MockTarget<>(Server.class));

        var actBody = server.string();
        assertThat(actBody).isEqualTo(expBody);
        mockClient.verifyStatus();

        var request = mockClient.verifyOne(HttpMethod.GET, "/string");
        assertThat(request.body()).isNull();
    }
}