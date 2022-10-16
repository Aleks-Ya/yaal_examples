import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationTest {
    @Test
    void integrationTest1() {
        assertEquals(5, Calculator.sum(2, 3));
    }
}