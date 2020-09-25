package property.source.default_source;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Можно задать путь к файлу свойств через свойства JVM: -Dconf=file:${user.dir}/any/file.properties
 * Если свойство "conf" не задано, будет использовано значение по-умолчанию.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DefConfig.class)
public class DefaultSourceTest {
    @Autowired
    private Environment env;

    @Test
    public void environmentFromClasspath() {
        assertThat(env.getProperty("planet.name"), equalTo("Neptune"));
    }
}