package assertj.custom.assertclass;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class PersonAssert extends AbstractAssert<PersonAssert, Person> {

    public PersonAssert(Person actual) {
        super(actual, PersonAssert.class);
    }

    public static PersonAssert assertThat(Person actual) {
        return new PersonAssert(actual);
    }

    public PersonAssert hasName(String fullName) {
        isNotNull();
        if (!actual.name().equals(fullName)) {
            failWithMessage("Expected person to have full name %s but was %s",
                    fullName, actual.name());
        }
        return this;
    }

    public PersonAssert isAdult() {
        isNotNull();
        if (actual.age() < 18) {
            failWithMessage("Expected person to be adult");
        }
        return this;
    }

    public PersonAssert ageIsPositive() {
        isNotNull();
        Assertions.assertThat(actual.age())
                .overridingErrorMessage("Age must be positive: actual=%d", actual.age())
                .isPositive();
        return this;
    }
}
