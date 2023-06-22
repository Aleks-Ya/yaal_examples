package hibernate.h2.config.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cashier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return String.format("Cashier[id=%d firstName=%s lastName=%s age=%d]", id, firstName, lastName, age);
    }

    public Cashier() {
    }

    public Cashier(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}