package java8.interface_default_method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * Вызов static метода интерфейса.
 */
public class StaticMethodTest {
    @Test
    public void staticMethod() {
        Car car = new CarImpl("BMW", 270);
        Car duplicate = Car.duplicate(car);
        assertNotSame(car, duplicate);
        assertEquals(car.getModel(), duplicate.getModel());
        assertEquals(car.getMaxSpeed(), duplicate.getMaxSpeed());
        assertEquals(car.carInfo(), duplicate.carInfo());
    }
}
