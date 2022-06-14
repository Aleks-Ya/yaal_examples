package properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPropertySourceTest.class)
@TestPropertySource(properties = {"person=John", "url=http://site.com:7777", "price=2.5", "costs=10.55"})
class TestPropertySourceTest {

    @Value("${person}")
    private String person;

    @Value("${url}")
    private URL url;

    @Value("${price}")
    private Double price;

    @Value("${costs}")
    private BigDecimal costs;

    @Test
    void properties() throws MalformedURLException {
        assertThat(person).isEqualTo("John");
        assertThat(url).isEqualTo(new URL("http://site.com:7777"));
        assertThat(price).isEqualTo(2.5D);
        assertThat(costs).isEqualTo(new BigDecimal("10.55"));
    }

}