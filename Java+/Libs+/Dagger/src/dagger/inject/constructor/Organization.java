package dagger.inject.constructor;

import javax.inject.Inject;

public class Organization {
    private final Person person;

    @Inject
    public Organization(Person person) {
        this.person = person;
    }
}
