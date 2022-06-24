package java11.interfaces;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrivateMethodInInterfaceTest {

    @Test
    void defaultPrivateMethod() {
        Names names = new Names() {
        };
        assertThat(names.getName()).isEqualTo("John");

    }

    interface Names {
        default String getName() {
            return getString();
        }

        private String getString() {
            return "John";
        }

    }
}
