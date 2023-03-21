package spel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValueTest.Data.class)
@TestPropertySource(properties = "city=London")
class ValueTest {

    @Autowired
    private Data data;

    @Test
    void javaSystemProperty() {
        System.out.println(data.userLanguage);
    }

    @Test
    void ternaryInteger() {
        assertThat(data.ternaryInteger).isEqualTo(33);
    }

    @Test
    void splitString() {
        assertThat(data.splitString).isEqualTo("host");
    }

    @Test
    void springProperty() {
        assertThat(data.springProperty).isEqualTo("London");
        assertThat(data.springProperty2).isEqualTo("London");
    }

    @Test
    void defaultValueNotEmpty() {
        assertThat(data.defaultNotEmpty).isEqualTo("BMW");
        assertThat(data.defaultEmpty).isEmpty();
        assertThat(data.defaultNull).isNull();
    }

    @Component
    static class Data {
        @Value("${city}")
        public String springProperty;
        @Value("#{'${city}'}")
        public String springProperty2;
        @Value("#{ systemProperties['user.language'] }")
        public String userLanguage;
        @Value("#{ systemProperties['not_exists']?:33 }")
        public Integer ternaryInteger;
        @Value("#{ 'host:port'.split(':')[0] }")
        public String splitString;
        @Value("${car:BMW}")
        public String defaultNotEmpty;
        @Value("${task:}")
        public String defaultEmpty;
        @Value("${size:#{null}}")
        public String defaultNull;
    }
}