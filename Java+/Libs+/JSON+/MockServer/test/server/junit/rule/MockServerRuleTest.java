package server.junit.rule;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import static org.hamcrest.Matchers.equalTo;;

public class MockServerRuleTest {

	private static final int PORT = 56698;
	@Rule
	public MockServerRule mockServerRule = new MockServerRule(this, PORT);
	private MockServerClient mockServerClient;

	@Test
	public void test() throws IOException {
		final String PATH = "/login";
		final String CONTENT_TYPE = "application/json; charset=utf-8";
		mockServerClient.when(request().withMethod("GET").withPath(PATH), exactly(1))
				.respond(response().withStatusCode(401)
						.withHeaders(new Header("Content-Type", CONTENT_TYPE),
								new Header("Cache-Control", "public, max-age=86400"))
						.withBody("{ message: 'incorrect username and password combination' }")
						.withDelay(new Delay(SECONDS, 1)));

		URL url = new URL("http://localhost:" + PORT + PATH);
		URLConnection connection = url.openConnection();
		assertThat(connection.getContentType(), equalTo(CONTENT_TYPE));
	}
}
