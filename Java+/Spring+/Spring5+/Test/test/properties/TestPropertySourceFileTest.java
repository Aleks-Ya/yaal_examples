package properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPropertySourceFileTest.class)
@TestPropertySource(locations = "classpath:properties/TestPropertySourceFileTest.properties")
class TestPropertySourceFileTest {

    @Value("${person.name}")
    private String name;

    @Test
    void properties() throws MalformedURLException {
        assertThat(name).isEqualTo("John");
    }

}