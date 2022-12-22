package jpa.eclipselink.inheritance.strategy.single_table;

import org.springframework.data.repository.CrudRepository;

interface AirplaneRepository extends CrudRepository<Airplane, Long> {
}
