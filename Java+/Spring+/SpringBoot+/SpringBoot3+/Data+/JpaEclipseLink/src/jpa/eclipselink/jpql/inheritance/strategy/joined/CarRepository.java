package jpa.eclipselink.jpql.inheritance.strategy.joined;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface CarRepository extends CrudRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.wheel = jpa.eclipselink.jpql.inheritance.strategy.joined.SteeringWheel.RIGHT AND c.available = true")
    List<Car> findAvailableRightWheelCars();
}
