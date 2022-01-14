package jpa.inheritance.strategy.single_table;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use default {@link javax.persistence.Inheritance} strategy ({@link javax.persistence.InheritanceType#SINGLE_TABLE}).
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Airplane.class, Car.class, CarRepository.class, AirplaneRepository.class})
class InheritanceSingleTableTest {
    @Autowired
    private CarRepository carRepo;

    @Autowired
    private AirplaneRepository airplaneRepo;

    @Test
    void carRepo() {
        var savedCar = carRepo.save(new Car(1L, "BMW", Car.SteeringWheel.RIGHT));
        var actCar = carRepo.findById(savedCar.getId());
        assertThat(actCar).hasValue(savedCar);
    }

    @Test
    void airplaneRepo() {
        var savedAirplane = airplaneRepo.save(new Airplane(2L, "SuperJet", 10_000));
        var actAirplane = airplaneRepo.findById(savedAirplane.getId());
        assertThat(actAirplane).hasValue(savedAirplane);
    }
}
