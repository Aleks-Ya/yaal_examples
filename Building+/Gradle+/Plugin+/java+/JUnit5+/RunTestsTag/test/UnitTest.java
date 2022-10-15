import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class UnitTest {

    @Test
    void unitTest1() {
    }

    @Test
    @Tag("slow")
    void unitTestSlow() {
    }

}
