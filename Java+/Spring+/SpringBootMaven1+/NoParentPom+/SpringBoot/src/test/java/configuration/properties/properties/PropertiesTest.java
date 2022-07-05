package configuration.properties.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropertiesConfig.class)
public class PropertiesTest {

    @Autowired
    private PropertiesSettings properties;

    @Test
    void properties() {
        assertThat(properties.getMessage()).isEqualTo("Hello world!"));
        assertThat(properties.getSystem().getStatus()).isEqualTo("System is OK."));
    }
}