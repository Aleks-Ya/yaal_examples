package jpa.eclipselink.inheritance.strategy.table_per_class;

import org.springframework.data.repository.CrudRepository;

interface CarRepository extends CrudRepository<Car, Long> {
}
