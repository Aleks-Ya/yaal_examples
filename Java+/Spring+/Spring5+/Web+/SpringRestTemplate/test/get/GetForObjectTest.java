package get;

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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class GetForObjectTest {

    @Test
    void getForObjectTest() {
        var restTemplate = new RestTemplate();

        var url = "/localhost";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        var response = restTemplate.getForObject(url, Quote.class);
        System.out.println(response);
    }

    @Test
    void withHeaders() {
        var restTemplate = new RestTemplate();
        var headerName = "myheader";
        var headerValue = "abc";
        var url = "/localhost";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(headerName, equalTo(headerValue)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        var headers = new HttpHeaders();
        headers.add(headerName, headerValue);

        var entity = new HttpEntity<>("body", headers);

        var responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}