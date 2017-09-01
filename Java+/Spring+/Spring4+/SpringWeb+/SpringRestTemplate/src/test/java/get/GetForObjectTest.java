package get;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import data.Quote;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class GetForObjectTest {

    @Test
    public void getForObjectTest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String url = "localhost";
        final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        Quote response = restTemplate.getForObject(url, Quote.class);
        System.out.println(response.toString());
    }

    @Test
    public void withHeaders() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String headerName = "myheader";
        String headerValue = "abc";
        final String url = "localhost";
        final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(headerName, equalTo(headerValue)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, headerValue);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}