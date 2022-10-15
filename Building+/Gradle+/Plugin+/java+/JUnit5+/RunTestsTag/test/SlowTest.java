import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Source: http://junit.org/junit5/docs/current/user-guide/#writing-tests-tagging-and-filtering
 */
@Tag("slow")
class SlowTest {

    @Test
    void slowTest1() {
    }

    @Test
    void slowTest2() {
    }

}
