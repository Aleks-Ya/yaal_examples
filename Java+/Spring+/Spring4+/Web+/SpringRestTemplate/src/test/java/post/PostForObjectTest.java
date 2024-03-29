package post;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import data.Quote;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PostForObjectTest {

    @Test
    void customHeader() {
        RestTemplate restTemplate = new RestTemplate();

        String headerName = "myheader";
        String headerValue = "abc";
        final String url = "localhost/path2";
        final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(headerName).isEqualTo(headerValue)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));


        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, headerValue);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        Quote quote = restTemplate.postForObject(url, entity, Quote.class);
        System.out.println(quote.toString());
    }

    @Test
    void bodyTest() {
        final RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String body = "{key: value}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        final String URL = "localhost/path";
        final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(URL)).andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("key").isEqualTo("value")))
                .andExpect(header("content-type").isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        Quote quote = restTemplate.postForObject(URL, entity, Quote.class);
        System.out.println(quote.toString());

        server.verify();
    }
}