package interfaces;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

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
