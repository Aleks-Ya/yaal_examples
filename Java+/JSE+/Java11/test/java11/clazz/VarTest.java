package java11.clazz;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VarTest {

    @Test
    void localVar() {
        var localVar = "abc";
        assertThat(localVar).isEqualTo("abc");
    }
}
