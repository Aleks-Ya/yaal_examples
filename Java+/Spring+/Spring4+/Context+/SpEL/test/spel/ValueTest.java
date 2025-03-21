package spel;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ValueTest.Data.class)
class ValueTest {

    @Autowired
    private Data data;

    @Test
    void main() {
        System.out.println(data.getUserLanguage());
    }

    @Test
    void ternaryInteger() {
        assertThat(data.getTernaryInteger()).isEqualTo(33);
    }

    @Component
    static class Data {
        @Value("#{ systemProperties['user.language'] }")
        private String userLanguage;

        @Value("#{ systemProperties['not_exists']?:33 }")
        private Integer ternaryInteger;

        String getUserLanguage() {
            return userLanguage;
        }

        Integer getTernaryInteger() {
            return ternaryInteger;
        }
    }
}