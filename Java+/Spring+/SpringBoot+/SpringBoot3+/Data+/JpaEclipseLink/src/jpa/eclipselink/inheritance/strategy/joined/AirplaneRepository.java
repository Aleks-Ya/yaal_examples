package jpa.eclipselink.inheritance.strategy.joined;

import org.springframework.data.repository.CrudRepository;

interface AirplaneRepository extends CrudRepository<Airplane, Long> {
}
