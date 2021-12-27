package lombok;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Docs: https://projectlombok.org/features/Builder
 */
class BuilderTest {

    @Test
    void builder() {
        var name = "Moscow";
        var age = 1000;
        var moscow = City.builder().name(name).age(age).build();
        assertThat(moscow.getName()).isEqualTo(name);
        assertThat(moscow.getAge()).isEqualTo(age);
    }

    @Builder
    @Value
    private static class City {
        String name;
        int age;
    }
}
