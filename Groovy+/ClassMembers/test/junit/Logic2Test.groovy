package junit

import org.junit.Assert
import org.junit.Test

class Logic2Test {
    Logic cut = new Logic()

    @Test
    void testGetString() {
        Assert.assertEquals('a string', cut.string)
    }

    @Test(expected = RuntimeException.class)
    void testThrowException() {
        cut.throwException()
    }
}