package feign;

import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import feign.mock.VerificationAssertionError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static feign.Util.toByteArray;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Source: https://github.com/OpenFeign/feign/blob/master/mock/src/test/java/feign/mock/MockClientTest.java
 */
class MockClientTest {

    private GitHub github;
    private MockClient mockClient;

    @BeforeEach
    void setup() throws IOException {
        try (var input = requireNonNull(getClass().getResourceAsStream("/feign/contributors.json"))) {
            var data = toByteArray(input);
            mockClient = new MockClient();
            github = Feign.builder().decoder(new AssertionDecoder(new GsonDecoder()))
                    .client(mockClient.ok(HttpMethod.GET, "/repos/netflix/feign/contributors", data)
                            .ok(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=55")
                            .ok(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=7 7",
                                    new ByteArrayInputStream(data))
                            .ok(HttpMethod.POST, "/repos/netflix/feign/contributors",
                                    "{\"login\":\"velo\",\"contributions\":0}")
                            .noContent(HttpMethod.PATCH, "/repos/velo/feign-mock/contributors")
                            .add(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=1234567890",
                                    HttpsURLConnection.HTTP_NOT_FOUND)
                            .add(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=123456789",
                                    HttpsURLConnection.HTTP_INTERNAL_ERROR, new ByteArrayInputStream(data))
                            .add(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=123456789",
                                    HttpsURLConnection.HTTP_INTERNAL_ERROR, "")
                            .add(HttpMethod.GET, "/repos/netflix/feign/contributors?client_id=123456789",
                                    HttpsURLConnection.HTTP_INTERNAL_ERROR, data))
                    .target(new MockTarget<>(GitHub.class));
        }
    }

    @Test
    void hitMock() {
        var contributors = github.contributors("netflix", "feign");
        assertThat(contributors).hasSize(30);
        mockClient.verifyStatus();
    }

    @Test
    void missMock() {
        try {
            github.contributors("velo", "feign-mock");
            fail();
        } catch (FeignException e) {
            assertThat(e.getMessage()).containsSubsequence("404");
        }
    }

    @Test
    void missHttpMethod() {
        try {
            github.patchContributors("netflix", "feign");
            fail();
        } catch (FeignException e) {
            assertThat(e.getMessage()).containsSubsequence("404");
        }
    }

    @Test
    void paramsEncoding() {
        var contributors = github.contributors("7 7", "netflix", "feign");
        assertThat(contributors).hasSize(30);
        mockClient.verifyStatus();
    }

    @Test
    void verifyInvocation() {
        var contribution =
                github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        // making sure it received a proper response
        assertThat(contribution).isNotNull();
        assertThat(contribution.login).isEqualTo("velo");
        assertThat(contribution.contributions).isEqualTo(0);

        var results =
                mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 1);
        assertThat(results).hasSize(1);

        var body = mockClient.verifyOne(HttpMethod.POST, "/repos/netflix/feign/contributors")
                .body();
        assertThat(body).isNotNull();

        var message = new String(body);
        assertThat(message).containsSubsequence("velo_at_github");
        assertThat(message).containsSubsequence("preposterous hacker");

        mockClient.verifyStatus();
    }

    @Test
    void verifyNone() {
        github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 1);

        try {
            mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 0);
            fail();
        } catch (VerificationAssertionError e) {
            assertThat(e.getMessage()).containsSubsequence("Do not wanted");
            assertThat(e.getMessage()).containsSubsequence("POST");
            assertThat(e.getMessage()).containsSubsequence("/repos/netflix/feign/contributors");
        }

        try {
            mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 3);
            fail();
        } catch (VerificationAssertionError e) {
            assertThat(e.getMessage()).containsSubsequence("Wanted");
            assertThat(e.getMessage()).containsSubsequence("POST");
            assertThat(e.getMessage()).containsSubsequence("/repos/netflix/feign/contributors");
            assertThat(e.getMessage()).containsSubsequence("'3'");
            assertThat(e.getMessage()).containsSubsequence("'1'");
        }
    }

    @Test
    void verifyNotInvoked() {
        mockClient.verifyNever(HttpMethod.POST, "/repos/netflix/feign/contributors");
        var results =
                mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 0);
        assertThat(results).hasSize(0);
        try {
            mockClient.verifyOne(HttpMethod.POST, "/repos/netflix/feign/contributors");
            fail();
        } catch (VerificationAssertionError e) {
            assertThat(e.getMessage()).containsSubsequence("Wanted");
            assertThat(e.getMessage()).containsSubsequence("POST");
            assertThat(e.getMessage()).containsSubsequence("/repos/netflix/feign/contributors");
            assertThat(e.getMessage()).containsSubsequence("never invoked");
        }
    }

    @Test
    void verifyNegative() {
        try {
            mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", -1);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).containsSubsequence("non negative");
        }
    }

    @Test
    void verifyMultipleRequests() {
        mockClient.verifyNever(HttpMethod.POST, "/repos/netflix/feign/contributors");

        github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        var result = mockClient.verifyOne(HttpMethod.POST, "/repos/netflix/feign/contributors");
        assertThat(result).isNotNull();

        github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        var results =
                mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 2);
        assertThat(results).hasSize(2);

        github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        results = mockClient.verifyTimes(HttpMethod.POST, "/repos/netflix/feign/contributors", 3);
        assertThat(results).hasSize(3);

        mockClient.verifyStatus();
    }

    @Test
    void resetRequests() {
        mockClient.verifyNever(HttpMethod.POST, "/repos/netflix/feign/contributors");

        github.create("netflix", "feign", "velo_at_github", "preposterous hacker");
        var result = mockClient.verifyOne(HttpMethod.POST, "/repos/netflix/feign/contributors");
        assertThat(result).isNotNull();

        mockClient.resetRequests();
        mockClient.verifyNever(HttpMethod.POST, "/repos/netflix/feign/contributors");
    }

    interface GitHub {

        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("GET /repos/{owner}/{repo}/contributors?client_id={client_id}")
        List<Contributor> contributors(@Param("client_id") String clientId,
                                       @Param("owner") String owner,
                                       @Param("repo") String repo);

        @RequestLine("PATCH /repos/{owner}/{repo}/contributors")
        List<Contributor> patchContributors(@Param("owner") String owner, @Param("repo") String repo);

        @RequestLine("POST /repos/{owner}/{repo}/contributors")
        @Body("%7B\"login\":\"{login}\",\"type\":\"{type}\"%7D")
        Contributor create(@Param("owner") String owner,
                           @Param("repo") String repo,
                           @Param("login") String login,
                           @Param("type") String type);

    }

    static class Contributor {
        String login;
        int contributions;
    }

    static class AssertionDecoder implements Decoder {
        private final Decoder delegate;

        public AssertionDecoder(Decoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            assertThat(response.request()).isNotNull();
            return delegate.decode(response, type);
        }

    }

}