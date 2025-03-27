package java8.interface_default_method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Вызов static метода интерфейса.
 */
class StaticMethodTest {
    @Test
    void staticMethod() {
        Car car = new CarImpl("BMW", 270);
        Car duplicate = Car.duplicate(car);
        assertThat(duplicate).isNotSameAs(car);
        assertThat(duplicate.getModel()).isEqualTo(car.getModel());
        assertThat(duplicate.getMaxSpeed()).isEqualTo(car.getMaxSpeed());
        assertThat(duplicate.carInfo()).isEqualTo(car.carInfo());
    }
}
