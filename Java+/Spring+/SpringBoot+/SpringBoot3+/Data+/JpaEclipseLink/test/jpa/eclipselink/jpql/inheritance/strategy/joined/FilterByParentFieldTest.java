package jpa.eclipselink.jpql.inheritance.strategy.joined;

import jpa.eclipselink.BaseEclipseLinkTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static jpa.eclipselink.jpql.inheritance.strategy.joined.SteeringWheel.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
class FilterByParentFieldTest extends BaseEclipseLinkTest {
    @Autowired
    private CarRepository carRepo;

    @Test
    void availableCars() {
        var savedCar1 = carRepo.save(new Car(1L, "BMW", RIGHT, true));
        carRepo.save(new Car(2L, "Mercedes", RIGHT, false));
        var actCar = carRepo.findAvailableRightWheelCars();
        assertThat(actCar).contains(savedCar1);
    }

}
