package get;

import data.Quote;
import data.Value;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class GetForEntityTest {

    @Test
    void getForEntity() {
        var restTemplate = new RestTemplate();

        var url = "/localhost";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"type\":\"City\", \"value\":{\"id\":1, \"quote\":\"Moscow\"}}", MediaType.APPLICATION_JSON));

        var entity = restTemplate.getForEntity(url, Quote.class);
        var quote = entity.getBody();

        var expValue = new Value();
        expValue.setId(1L);
        expValue.setQuote("Moscow");
        var expQuote = new Quote();
        expQuote.setType("City");
        expQuote.setValue(expValue);

        assertThat(quote.toString(), equalTo(expQuote.toString()));
    }

    @Test
    void getForByteArray() {
        var restTemplate = new RestTemplate();

        var url = "/localhost";
        var server = MockRestServiceServer.bindTo(restTemplate).build();
        var body = "the body";
        server.expect(once(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        var entity = restTemplate.getForEntity(url, byte[].class);
        var quote = new String(Objects.requireNonNull(entity.getBody()), Charset.defaultCharset());

        assertThat(quote, equalTo(body));
    }

}