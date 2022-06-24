package feign;

import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

class AssertHttpStatusTest {

    interface Server {
        @RequestLine("GET /string")
        String string();
    }

    static class AssertionDecoder implements Decoder {
        private final Decoder delegate;

        public AssertionDecoder(Decoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            assertThat(response.status()).isEqualTo(200);
            return delegate.decode(response, type);
        }
    }

    @Test
    void status() {
        var expBody = "abc";
        var mockClient = new MockClient().ok(HttpMethod.GET, "/string", expBody);
        var server = Feign.builder()
                .decoder(new AssertionDecoder(new GsonDecoder()))
                .client(mockClient)
                .target(new MockTarget<>(StringBodyTest.Server.class));

        var actBody = server.string();
        assertThat(actBody).isEqualTo(expBody);
        mockClient.verifyStatus();
    }
}