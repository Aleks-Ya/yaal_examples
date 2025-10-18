package assertj.custom.assertclass;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowingConsumer;

public class PersonAssert extends AbstractAssert<PersonAssert, Person> {

    private PersonAssert(Person actual) {
        super(actual, PersonAssert.class);
    }

    public static PersonAssert assertThat(Person actual) {
        return new PersonAssert(actual);
    }

    @SuppressWarnings("UnusedReturnValue")
    public PersonAssert hasName(String fullName) {
        isNotNull();
        objects.assertEqual(info, actual.name(), fullName);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public PersonAssert isAdult() {
        isNotNull();
        if (actual.age() < 18) {
            failWithMessage("Expected person to be adult");
        }
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public PersonAssert ageIsPositive() {
        isNotNull();
        Assertions.assertThat(actual.age())
                .overridingErrorMessage("Age must be positive: actual=%d", actual.age())
                .isPositive();
        return this;
    }

    @SafeVarargs
    @SuppressWarnings("UnusedReturnValue")
    public final PersonAssert hasCars(ThrowingConsumer<? super Car>... requirements) {
        Assertions.assertThat(actual.cars()).satisfiesExactly(requirements);
        return this;
    }
}
