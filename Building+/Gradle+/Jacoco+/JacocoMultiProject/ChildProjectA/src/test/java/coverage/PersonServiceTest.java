package coverage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonServiceTest {
    @Test
    void getListPersons() {
        var service = new PersonService();
        var persons = service.getPersons();
        assertEquals(List.of("John", "Mary", "Mark"), persons);
    }
}