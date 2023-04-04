package method_execution_time.builtin.static_logger;

class Person {
    private final String lastName;
    private final String firstName;

    Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getFirstName() {
        return firstName;
    }
}
