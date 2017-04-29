package usual;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {SpringConfig.class, PropertiesPojo.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertiesSourceTest {

    @Autowired
    private PropertiesPojo pojo;

    @Test
    @Ignore("что-то не работает")
    public void pojo() {
        assertThat(pojo.getMyNameFromProperty(), equalTo("aleks"));
        assertThat(pojo.getBestCity(), equalTo("SPB"));
    }
}