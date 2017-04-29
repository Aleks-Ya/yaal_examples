package configuration.properties.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropertiesSettings.class)
public class PropertiesTest {

    @Autowired
    private PropertiesSettings properties;

    @Test
    public void properties() {
        assertThat(properties.getMessage(), equalTo("Hello world!"));
        assertThat(properties.getSystem().getStatus(), equalTo("System is OK."));
    }
}