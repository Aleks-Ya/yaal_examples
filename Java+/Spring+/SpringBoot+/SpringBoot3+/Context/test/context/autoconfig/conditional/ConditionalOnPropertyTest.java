package context.autoconfig.conditional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE, properties = ConditionalOnPropertyTest.STORAGE_ENABLED_PROPERTY + "=true")
@SpringBootConfiguration
class ConditionalOnPropertyTest {
    public static final String STORAGE_ENABLED_PROPERTY = "storage.enabled";

    @Test
    void conditional(ApplicationContext context) {
        assertThat(context.getBean(Storage.class)).isNotNull();
    }

    @Component
    @ConditionalOnProperty(STORAGE_ENABLED_PROPERTY)
    static class Storage {
    }
}