package java8.interface_default_method;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

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
