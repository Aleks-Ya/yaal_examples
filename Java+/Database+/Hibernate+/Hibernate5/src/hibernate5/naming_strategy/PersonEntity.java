package hibernate5.naming_strategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String personName;

    private Integer personAge;

    public PersonEntity() {
    }

    public PersonEntity(String personName, Integer personAge) {
        this.personName = personName;
        this.personAge = personAge;
    }

    @Override
    public String toString() {
        return String.format("Person[id=%d, personName=%s, personAge=%d]", id, personName, personAge);
    }
}
