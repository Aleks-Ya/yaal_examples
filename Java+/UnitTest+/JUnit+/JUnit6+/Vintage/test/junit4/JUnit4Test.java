package junit4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("JUnit5Converter")
public class JUnit4Test {
    private boolean value;

    @Before
    public void before() {
        value = true;
    }

    @Test
    public void success() {
        assertTrue(value);
    }
}
