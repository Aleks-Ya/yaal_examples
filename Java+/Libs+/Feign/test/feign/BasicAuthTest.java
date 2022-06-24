package feign;

import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicAuthTest {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER = "the_user";
    private static final String PASS = "the_pass";

    interface Server {
        @RequestLine("GET /string")
        @Headers(AUTHORIZATION_HEADER + ": Basic {basic}")
        String string(@Param("basic") String basic);
    }

    @Test
    void basicAuth() {
        var expBody = "the_body";

        var mockClient = new MockClient().ok(HttpMethod.GET, "/string", expBody);
        var server = Feign.builder().client(mockClient).target(new MockTarget<>(Server.class));

        var bytes = (USER + ":" + PASS).getBytes();
        var basic = Base64.encodeBase64String(bytes);
        var actBody = server.string(basic);

        assertThat(actBody).isEqualTo(expBody);
        mockClient.verifyStatus();

        var request = mockClient.verifyOne(HttpMethod.GET, "/string");
        var authHeader = request.headers().get(AUTHORIZATION_HEADER).stream()
                .findFirst().orElseThrow()
                .replace("Basic ", "");
        var split = new String(Base64.decodeBase64(authHeader)).split(":");
        var username = split[0];
        var password = split[1];
        assertThat(username).isEqualTo(USER);
        assertThat(password).isEqualTo(PASS);
    }
}