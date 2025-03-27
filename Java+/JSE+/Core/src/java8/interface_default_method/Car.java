package java8.interface_default_method;

/**
 * Интерфейс с default и static-методами.
 */
public interface Car {
    String getModel();

    int getMaxSpeed();

    default String carInfo() {
        return "Car[" + getModel() + "," + getMaxSpeed() + "]";
    }

    static Car duplicate(Car car) {
        return new CarImpl(car.getModel(), car.getMaxSpeed());
    }
}
