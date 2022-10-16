import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationIT {
    @Test
    void integrationTest1() {
        assertEquals(5, Calculator.sum(2, 3));
    }
}