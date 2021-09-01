package coverage;

import java.util.List;
import java.util.stream.Collectors;

class PersonService {
    private static final List<String> persons = List.of("John", "Mary", "Mark");

    List<String> getPersons() {
        return persons.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    String getAdminPerson() {
        return "Not Covered by tests";
    }
}