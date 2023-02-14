package jpa.eclipselink.inheritance.strategy.joined;

import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
class Car extends Vehicle {
    private SteeringWheel wheel;

    public Car() {
    }

    public Car(Long id, String name, SteeringWheel wheel) {
        super(id, name);
        this.wheel = wheel;
    }

    public SteeringWheel getWheel() {
        return wheel;
    }

    public void setWheel(SteeringWheel wheel) {
        this.wheel = wheel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return wheel == car.wheel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wheel);
    }

    public enum SteeringWheel {
        RIGHT, LEFT
    }
}
