package validation.valid;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

class Person {
    @NotEmpty
    public final String firstName;

    @NotEmpty
    public final String secondName;

    @Email
    public final String email;

    @Valid
    @NotNull
    public final Car car;

    Person(String firstName, String secondName, String email, Car car) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.car = car;
    }
}
