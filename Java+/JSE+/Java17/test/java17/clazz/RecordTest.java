package java17.clazz;

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

    private record Person(Long id, String name) {
    }

    @Test
    void recordWithFactoryMethod() {
        var john = Person2.create(1L, "John");
        assertThat(john).isEqualTo(new Person2(1L, "JOHN"));
    }

    private record Person2(Long id, String name) {
        public static Person2 create(Long id, String name) {
            return new Person2(id, name.toUpperCase());
        }
    }
}
