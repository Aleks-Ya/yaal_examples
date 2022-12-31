package java17.clazz.record;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Scala case classes. POJO.
 */
class RecordTest {

    @Test
    void record() {
        var john = new Person(1L, "John");
        var john2 = new Person(1L, "John");
        assertThat(john).isEqualTo(john2);
    }

    @Test
    void recordWithFactoryMethod() {
        var john = Person2.create(1L, "John");
        assertThat(john).isEqualTo(new Person2(1L, "JOHN"));
    }

    @Test
    void recordWithCalculatingMethod() {
        var john = new Person3(1L, "John", 30);
        assertThat(john.id()).isEqualTo(1L);
        assertThat(john.name()).isEqualTo("John");
        assertThat(john.adult()).isTrue();
    }

    @Test
    void additionalConstructor() {
        var john = new Person4(1L);
        assertThat(john).isEqualTo(new Person4(1L, "John"));
    }

    private record Person(Long id, String name) {
    }

    private record Person2(Long id, String name) {
        public static Person2 create(Long id, String name) {
            return new Person2(id, name.toUpperCase());
        }
    }

    private record Person3(Long id, String name, Integer age) {
        public boolean adult() {
            return age >= 18;
        }
    }

    private record Person4(Long id, String name) {
        public Person4(Long id) {
            this(id, "John");
        }
    }
}
