package coverage;

import java.util.List;

class PersonService {
    List<String> getPersons() {
        return List.of("John", "Mary", "Mark");
    }

    @SuppressWarnings("unused")
    String getAdminPerson() {
        return "Not Covered by tests";
    }
}