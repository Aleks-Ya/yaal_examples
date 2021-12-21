package jpa.record;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PersonRecord")
@Table(name = "persons_record")
record Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
