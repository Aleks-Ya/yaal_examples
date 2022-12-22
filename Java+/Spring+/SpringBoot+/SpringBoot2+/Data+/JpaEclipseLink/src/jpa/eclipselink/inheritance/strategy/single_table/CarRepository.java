package jpa.eclipselink.inheritance.strategy.single_table;

import org.springframework.data.repository.CrudRepository;

interface CarRepository extends CrudRepository<Car, Long> {
}
