package jpa.hibernate.inheritance.strategy.table_per_class;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use {@link javax.persistence.Inheritance} strategy ({@link javax.persistence.InheritanceType#TABLE_PER_CLASS}).
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Airplane.class, Car.class, CarRepository.class, AirplaneRepository.class})
class InheritanceTablePerClassTest {
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
