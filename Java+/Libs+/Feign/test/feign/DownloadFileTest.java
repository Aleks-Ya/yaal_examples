package feign;

import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadFileTest {

    interface Server {
        @RequestLine("GET /file")
        byte[] file();
    }

    @Test
    public void body() {
        var fileContent = "abc".getBytes();
        var mockClient = new MockClient().ok(HttpMethod.GET, "/file", fileContent);
        var server = Feign.builder().client(mockClient).target(new MockTarget<>(Server.class));
        var actBody = server.file();
        assertThat(actBody).isEqualTo(fileContent);
        mockClient.verifyStatus();
    }
}