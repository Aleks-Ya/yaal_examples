package guide.tag;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Source: http://junit.org/junit5/docs/current/user-guide/#writing-tests-tagging-and-filtering
 */
@Tag("fast")
@Tag("model")
class TaggingDemo {

    @Test
    @Tag("taxes")
    void testingTaxCalculation() {
    }

}
