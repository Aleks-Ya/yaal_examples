package jpa.hibernate.record;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
