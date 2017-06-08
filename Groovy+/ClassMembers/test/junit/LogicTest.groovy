package junit

class LogicTest extends GroovyTestCase {
    Logic cut = new Logic()

    void setUp() {
        println('set up')
    }

    void tearDown() {
        println('tear down')
    }

    void testGetString() {
        GroovyTestCase.assertEquals('a string', cut.string)
    }

    //не работает
//    void testThrowException() {
//        cut.throwException()
//    }
}