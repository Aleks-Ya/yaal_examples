package jpa.entity.id;

import java.io.Serializable;
import java.util.Objects;

class Person2Id implements Serializable {
    private String firstName;

    private String secondName;

    public Person2Id() {
    }

    public Person2Id(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2Id personId = (Person2Id) o;
        return Objects.equals(firstName, personId.firstName) && Objects.equals(secondName, personId.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName);
    }
}
