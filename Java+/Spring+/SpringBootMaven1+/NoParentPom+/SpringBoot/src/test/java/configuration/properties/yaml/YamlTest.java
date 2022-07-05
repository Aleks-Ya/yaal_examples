package configuration.properties.yaml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YamlConfig.class)
public class YamlTest {

    @Autowired
    private YamlSettings yaml;

    @Test
    void yaml() {
        assertThat(yaml.isEnabled()).isEqualTo(true));
        assertThat(yaml.getMessage().getPrefix()).isEqualTo("Hi, everybody!"));
        assertThat(yaml.getMessage().getSuffix()).isEqualTo("Good bye!"));
        assertThat(yaml.getMessage().getUnderwrite()).isEqualTo(""));
    }
}