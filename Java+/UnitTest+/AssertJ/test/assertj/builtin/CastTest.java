package assertj.builtin;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CastTest {

    @Test
    void string() {
        Object str = "abc";
        assertThat(str).asInstanceOf(InstanceOfAssertFactories.STRING).hasSize(3);
    }

    @Test
    void typedList() {
        List<?> list = List.of("a", "b");
        assertThat(list).asInstanceOf(InstanceOfAssertFactories.LIST).contains("a", "b");
        assertThat(list).asInstanceOf(InstanceOfAssertFactories.list(String.class)).contains("a", "b");
    }

    @Test
    void untypedList() {
        List list = List.of("a", "b");
        assertThat(list).contains("a", "b");
        assertThat(list).asInstanceOf(InstanceOfAssertFactories.list(String.class)).asList().contains("a", "b");
        assertThat(list, ListAssert.class).contains("a", "b");
    }

}
