package lombok;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class BuilderTest {

    @Test
    void test() {
        var name = "Moscow";
        var age = 1000;
        var moscow = City.builder().name(name).age(age).build();
        assertThat(moscow.getName(), equalTo(name));
        assertThat(moscow.getAge(), equalTo(age));
    }

    @Builder
    @Value
    private static class City {
        String name;
        int age;
    }
}
