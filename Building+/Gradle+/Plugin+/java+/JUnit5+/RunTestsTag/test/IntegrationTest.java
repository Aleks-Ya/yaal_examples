import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Source: http://junit.org/junit5/docs/current/user-guide/#writing-tests-tagging-and-filtering
 */
@Tag("integration")
class IntegrationTest {

    @Test
    void integrationTest1() {
    }

    @Test
    @Tag("slow")
    void integrationTestSlow() {
    }

}
