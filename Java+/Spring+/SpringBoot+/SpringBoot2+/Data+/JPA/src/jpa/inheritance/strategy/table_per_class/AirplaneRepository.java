package jpa.inheritance.strategy.table_per_class;

import org.springframework.data.repository.CrudRepository;

interface AirplaneRepository extends CrudRepository<Airplane, Long> {
}