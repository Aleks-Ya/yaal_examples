package spring.web.rest;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class GetForObjectTest {

	@Test
	public void getForObjectTest() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		System.out.println(quote.toString());
	}

}