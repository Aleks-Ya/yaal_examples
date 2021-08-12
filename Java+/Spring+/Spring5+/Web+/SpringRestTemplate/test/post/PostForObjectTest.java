package post;

import data.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class PostForObjectTest {

    @Test
    void customHeader() {
        var restTemplate = new RestTemplate();

        var headerName = "myheader";
        var headerValue = "abc";
        var url = "/localhost/path2";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(headerName, equalTo(headerValue)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));


        var headers = new HttpHeaders();
        headers.add(headerName, headerValue);
        var entity = new HttpEntity<>("body", headers);

        var quote = restTemplate.postForObject(url, entity, Quote.class);
        System.out.println(quote);
    }

    @Test
    void bodyTest() {
        final var restTemplate = new RestTemplate();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = "{key: value}";
        var entity = new HttpEntity<>(body, headers);

        var URL = "/localhost/path";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(URL)).andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("key", equalTo("value")))
                .andExpect(header("content-type", equalTo(MediaType.APPLICATION_JSON_VALUE)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        var quote = restTemplate.postForObject(URL, entity, Quote.class);
        System.out.println(quote);

        server.verify();
    }
}