package ru.yaal.examples.database.hibernate.inheritance.joined.discriminator;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_joined_discriminator")
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 31)
@DiscriminatorValue("car_join_discriminator")
public class CarJoinedDiscriminator extends TransportJoinedDiscriminator {
    private String fuel;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
