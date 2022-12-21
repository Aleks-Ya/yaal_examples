package lombok;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetterTest {

    @Test
    void setter() {
        var name = "Moscow";
        var age = 1000;
        var city = new City();
        city.setName(name);
        city.setAge(age);
        assertThat(city.name).isEqualTo(name);
        assertThat(city.age).isEqualTo(age);
    }

    @Setter
    private static class City {
        String name;
        int age;
    }
}
