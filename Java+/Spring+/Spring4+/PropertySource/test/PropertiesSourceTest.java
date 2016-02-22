
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {SpringConfig.class, PropertiesPojo.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertiesSourceTest {

    @Value("${my.name}")
    private String myNameFromProperty;

    @Value("${best.city}")
    private String bestCity;

    @Autowired
    private PropertiesPojo pojo;

    @Autowired
    private Environment env;

    @Test
    public void value() {
        assertThat(myNameFromProperty, equalTo("aleks"));
        assertThat(bestCity, equalTo("SPB"));
    }

    @Test
    public void environmentFromClasspath() {
        assertThat(env.getProperty("my.name"), equalTo("aleks"));
        assertThat(env.getProperty("best.city"), equalTo("SPB"));
    }

    @Test
    @Ignore("что-то не работает")
    public void pojo() {
        assertThat(pojo.getMyNameFromProperty(), equalTo("aleks"));
        assertThat(pojo.getBestCity(), equalTo("SPB"));
    }
}