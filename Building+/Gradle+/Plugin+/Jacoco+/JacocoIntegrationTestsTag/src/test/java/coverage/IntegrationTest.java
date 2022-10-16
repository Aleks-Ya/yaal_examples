package coverage;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integration")
class IntegrationTest {
    @Test
    void getFemalePersons() {
        var service = new PersonService();
        var persons = service.getIntegrationTestPersons();
        assertEquals(List.of("Mary", "Ann"), persons);
    }
}