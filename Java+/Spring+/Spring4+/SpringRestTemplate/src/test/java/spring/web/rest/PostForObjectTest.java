package spring.web.rest;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
}