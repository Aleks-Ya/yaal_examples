package java17.clazz.record;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecordToStringTest {

    @Test
    void recordToString() {
        var john = new Person(1L, "John");
        assertThat(john).hasToString("Person[id=1, name=John]");
    }

    private record Person(Long id, String name) {
    }


}
