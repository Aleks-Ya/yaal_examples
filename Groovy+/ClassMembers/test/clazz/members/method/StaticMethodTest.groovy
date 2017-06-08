package clazz.members.method

import org.junit.Assert
import org.junit.Test

class StaticMethodTest {
    @Test
    void main() {
        Assert.assertEquals(StaticMethod.getOne(), 1)
    }
}