package method_execution_time.builtin;

class Person {
    private String lastName;
    private String firstName;

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
