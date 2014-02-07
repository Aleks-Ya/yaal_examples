package ru.yaal.examples.database.hibernate.inheritance.joined;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_joined")
public class CarJoined extends TransportJoined {
    private String fuel;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
