package ru.yaal.examples.database.hibernate.inheritance.single;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_single")
public class CarSingle extends TransportSingle {
    private String fuel;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
