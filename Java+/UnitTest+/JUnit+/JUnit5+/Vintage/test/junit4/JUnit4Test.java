package junit4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class JUnit4Test {
    private boolean value;

    @BeforeEach
    public void before() {
        value = true;
    }

    @Test
    public void success() {
        assertTrue(value);
    }
}
