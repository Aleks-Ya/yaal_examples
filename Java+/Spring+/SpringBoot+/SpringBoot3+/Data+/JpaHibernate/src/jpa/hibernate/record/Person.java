package jpa.hibernate.record;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PersonRecord")
@Table(name = "persons_record")
record Person(
        @Id
        @GeneratedValue
        Integer id,
        String name
) {
    public Person() {
        this(null, null);
    }

    public Person(String name) {
        this(null, name);
    }
}
