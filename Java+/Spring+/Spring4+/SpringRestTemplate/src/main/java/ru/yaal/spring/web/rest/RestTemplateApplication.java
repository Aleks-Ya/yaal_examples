package ru.yaal.spring.web.rest;

import org.springframework.web.client.RestTemplate;

/**
 * The main class.
 */
public class RestTemplateApplication {
	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		System.out.println(quote.toString());
	}
}