package java8.interface_default_method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Вызов default метода интерфейса.
 */
class DefaultMethodTest {
    @Test
    void defaultMethod() {
        Car car = new CarImpl("BMW", 270);
        assertThat(car.carInfo()).isEqualTo("Car[BMW,270]");
    }
}
