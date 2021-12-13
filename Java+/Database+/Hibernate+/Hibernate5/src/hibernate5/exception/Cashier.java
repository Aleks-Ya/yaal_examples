package hibernate5.exception;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Cashier {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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