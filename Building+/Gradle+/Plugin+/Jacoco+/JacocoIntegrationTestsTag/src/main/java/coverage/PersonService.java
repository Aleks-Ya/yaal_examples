package coverage;

import java.util.List;

class PersonService {
    List<String> getUnitTestPersons() {
        return List.of("John", "Mark");
    }

    List<String> getIntegrationTestPersons() {
        return List.of("Mary", "Ann");
    }

    @SuppressWarnings("unused")
    String getAdminPerson() {
        return "Not Covered by tests";
    }
}