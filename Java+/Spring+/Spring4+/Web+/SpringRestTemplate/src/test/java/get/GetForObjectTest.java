package get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import data.Quote;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class GetForObjectTest {

    @Test
    void getForObjectTest() throws Exception {
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
    void withHeaders() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(json, User.class);


        HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.setMessageConverters();

        String headerName = "myheader";
        String headerValue = "abc";
        final String url = "localhost";
        final MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(headerName).isEqualTo(headerValue)))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, headerValue);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}