package jpa.eclipselink.inheritance.strategy.joined;

import org.springframework.data.repository.CrudRepository;

interface CarRepository extends CrudRepository<Car, Long> {
}
