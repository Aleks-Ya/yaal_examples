package jpa.jpql.inheritance.strategy.joined;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static jpa.jpql.inheritance.strategy.joined.SteeringWheel.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Car.class, CarRepository.class})
class FilterByParentFieldTest {
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
