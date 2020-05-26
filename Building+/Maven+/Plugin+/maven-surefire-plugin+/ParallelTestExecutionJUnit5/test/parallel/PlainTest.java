package parallel;


import org.junit.jupiter.api.Test;

class PlainTest extends BaseTest {

    @Test
    public void test1() {
        printCurrentThread();
        wait3Seconds();
    }

    @Test
    public void test2() {
        printCurrentThread();
        wait3Seconds();
    }
}