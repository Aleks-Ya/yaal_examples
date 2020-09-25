package server.junit.rule;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

public class ClientAndServerTest {

	private static final int PORT = 56699;
	private static ClientAndServer mockServer;

	@BeforeClass
	public static void beforeClass() {
		mockServer = startClientAndServer(PORT);
	}

	@Test
	public void test() throws IOException {
		final String PATH = "/login";
		final String CONTENT_TYPE = "application/json; charset=utf-8";

		mockServer.when(request().withMethod("GET").withPath(PATH), exactly(1))
				.respond(response().withStatusCode(401).withHeaders(new Header("Content-Type", CONTENT_TYPE))
						.withBody("{ message: 'incorrect username and password combination' }"));

		URL url = new URL("http://localhost:" + PORT + PATH);
		URLConnection connection = url.openConnection();
		assertThat(connection.getContentType(), equalTo(CONTENT_TYPE));
	}

	@AfterClass
	public static void afterClass() {
		mockServer.stop();
	}

}
