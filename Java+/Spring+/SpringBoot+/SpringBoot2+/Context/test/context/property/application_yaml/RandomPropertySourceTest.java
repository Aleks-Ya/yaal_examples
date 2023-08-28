package context.property.application_yaml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static utilt.UuidAsserts.UUID_STRING;

/**
 * application.yaml uses properties defined in a @PropertySource.
 */
@SpringBootTest(webEnvironment = NONE, classes = RandomPropertySourceTest.class, properties =
        "spring.config.location=classpath:context/property/application_yaml/RandomPropertySourceTest.yaml")
@Configuration
class RandomPropertySourceTest {

    @Value("${info.number.integer}")
    private Integer randomInt;

    @Value("${info.number.long}")
    private Long randomLong;

    @Value("${info.uuid}")
    private UUID randomUuid;

    @Value("${info.string32}")
    private String randomString32Chars;

    @Autowired
    private Environment env;

    @Value("#{T(java.util.UUID).randomUUID().toString()}")
    private String randomUuidStatic1;

    @Value("${info.uuidStatic}")
    private String randomUuidStatic2;

    @Value("${info.uuidStatic}")
    private String randomUuidStatic3;


    @Test
    void randomFromValue() {
        System.out.println(randomInt);
        System.out.println(randomLong);
        System.out.println(randomUuid);
        System.out.println(randomString32Chars);
        assertThat(randomInt).isNotNull();
        assertThat(randomLong).isNotNull();
        assertThat(randomUuid).isInstanceOf(UUID.class).isNotNull();
        assertThat(randomString32Chars).isNotNull();
    }

    @Test
    void randomFromEnv() {
        var random1 = env.getProperty("random.int", Integer.class);
        assertThat(random1).isNotNull();
    }

    /**
     * New random value is generated on each property reading.
     */
    @Test
    void newRandomValue() {
        var random1 = env.getProperty("info.number.integer", Integer.class);
        assertThat(random1).isNotNull();
        var random2 = env.getProperty("info.number.integer", Integer.class);
        assertThat(random2).isNotNull().isNotEqualTo(random1);
    }

    @Test
    void randomUuidByStaticMethod() {
        assertThat(randomUuidStatic1).is(UUID_STRING);
        assertThat(randomUuidStatic2).is(UUID_STRING);
        assertThat(randomUuidStatic3).is(UUID_STRING).isNotEqualTo(randomUuidStatic2);
    }
}