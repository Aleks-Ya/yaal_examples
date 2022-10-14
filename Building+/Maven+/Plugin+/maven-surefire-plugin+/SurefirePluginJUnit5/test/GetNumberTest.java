import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetNumberTest {
    @Test
    void testGetNumber() {
        assertEquals(1, GetNumber.getNumber());
    }
}