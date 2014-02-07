package ru.yaal.examples.database.hibernate.inheritance.tableperclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "transport_table_per_class")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TransportTablePerClass {
    @Id
    @GeneratedValue
    private Long id;
    private String owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
