package java8.interface_default_method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Вызов static метода интерфейса.
 */
public class StaticMethodTest {
    @Test
    void staticMethod() {
        Car car = new CarImpl("BMW", 270);
        Car duplicate = Car.duplicate(car);
        assertThat(duplicate).isNotSameAs(car);
        assertEquals(car.getModel(), duplicate.getModel());
        assertEquals(car.getMaxSpeed(), duplicate.getMaxSpeed());
        assertEquals(car.carInfo(), duplicate.carInfo());
    }
}
