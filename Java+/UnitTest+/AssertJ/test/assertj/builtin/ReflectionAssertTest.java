package assertj.builtin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionAssertTest {

    /**
     * @see <a href="https://github.com/assertj/assertj-core/issues/1693">add constructor assertions for ClassAssert</a>
     */
    @Test
    void hasConstructor() throws NoSuchMethodException {
        assertThat(Person.class.getDeclaredConstructor(String.class, Integer.class)).isNotNull();
    }

    static class Person {
        Person(String name, Integer age) {
        }
    }
}
