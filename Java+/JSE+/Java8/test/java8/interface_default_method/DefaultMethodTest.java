package java8.interface_default_method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Вызов default метода интерфейса.
 */
public class DefaultMethodTest {
    @Test
    public void defaultMethod() {
        Car car = new CarImpl("BMW", 270);
        assertEquals("Car[BMW,270]", car.carInfo());
    }
}
