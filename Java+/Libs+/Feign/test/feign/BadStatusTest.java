package feign;

import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.junit.jupiter.api.Test;

import static feign.mock.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BadStatusTest {

    interface Server {
        @RequestLine("GET /string")
        String string();
    }

    @Test
    public void bad() {
        var errorMessage = "I'm a teapot!";

        var mockClient = new MockClient().add(GET, "/string", 418, errorMessage);
        var server = Feign.builder().client(mockClient).target(new MockTarget<>(Server.class));

        assertThatThrownBy(server::string)
                .isInstanceOf(FeignException.FeignClientException.class)
                .hasMessageContaining("[418 Mocked] during [GET] to [/string] [Server#string()]: [I'm a teapot!]");

        var request = mockClient.verifyOne(GET, "/string");
        assertThat(request.url()).isEqualTo("/string");
    }
}