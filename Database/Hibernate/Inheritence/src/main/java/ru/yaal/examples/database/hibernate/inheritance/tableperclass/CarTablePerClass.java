package ru.yaal.examples.database.hibernate.inheritance.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_table_per_class")
public class CarTablePerClass extends TransportTablePerClass {
    private String fuel;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
