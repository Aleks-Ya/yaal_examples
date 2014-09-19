import org.junit.Test

import static junit.framework.Assert.assertEquals;

class Logic2Test {
    Logic cut = new Logic()

    @Test
    void testGetString() {
        assertEquals('a string', cut.string)
    }

    @Test(expected = RuntimeException.class)
    void testThrowException() {
        cut.throwException()
    }
}