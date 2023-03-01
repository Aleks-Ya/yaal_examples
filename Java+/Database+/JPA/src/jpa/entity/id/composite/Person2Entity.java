package jpa.entity.id.composite;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Person2Entity.Person2Id.class)
class Person2Entity {
    @Id
    private String firstName;
    @Id
    private String secondName;
    @Id
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    public Person2Entity() {
    }

    public Person2Entity(String firstName, String secondName, Gender gender, Integer age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.age = age;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2Entity that = (Person2Entity) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName)
                && gender == that.gender && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, gender, age);
    }

    enum Gender {
        MALE, FEMALE
    }

    static class Person2Id implements Serializable {
        private String firstName;

        private String secondName;

        private Gender gender;

        public Person2Id() {
        }

        public Person2Id(String firstName, String secondName, Gender gender) {
            this.firstName = firstName;
            this.secondName = secondName;
            this.gender = gender;
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

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person2Id person2Id = (Person2Id) o;
            return Objects.equals(firstName, person2Id.firstName) && Objects.equals(secondName, person2Id.secondName)
                    && gender == person2Id.gender;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, secondName, gender);
        }
    }
}
