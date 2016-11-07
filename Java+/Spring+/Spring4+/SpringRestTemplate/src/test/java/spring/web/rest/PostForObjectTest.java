package spring.web.rest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class PostForObjectTest {

	@Test
	public void postForObjectTest() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.postForObject("http://echo.jsontest.com/type/myquote", headers, Quote.class);
		System.out.println(quote.toString());
	}

	@Test
	public void bodyTest() throws Exception {
		final RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		String body = "{key: value}";
		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		final String URL = "localhost/path";
		final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
		server.expect(once(), requestTo(URL)).andExpect(method(HttpMethod.POST))
				.andExpect(jsonPath("key", equalTo("value")))
				.andExpect(header("content-type", equalTo(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

		Quote quote = restTemplate.postForObject(URL, entity, Quote.class);
		System.out.println(quote.toString());

		server.verify();
	}
}