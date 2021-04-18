package java11.interfaces;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PrivateMethodInInterface {

    @Test
    public void defaultPrivateMethod() {
        Names names = new Names() {
        };
        assertThat(names.getName(), equalTo("John"));

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
