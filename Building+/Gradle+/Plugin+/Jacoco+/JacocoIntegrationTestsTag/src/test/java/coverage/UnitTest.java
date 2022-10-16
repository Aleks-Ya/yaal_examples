package coverage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitTest {
    @Test
    void getMalePersons() {
        var service = new PersonService();
        var persons = service.getUnitTestPersons();
        assertEquals(List.of("John", "Mark"), persons);
    }
}