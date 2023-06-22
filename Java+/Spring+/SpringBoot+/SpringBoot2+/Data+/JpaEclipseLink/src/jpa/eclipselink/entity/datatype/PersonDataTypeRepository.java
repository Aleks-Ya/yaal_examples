package jpa.eclipselink.entity.datatype;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface PersonDataTypeRepository extends CrudRepository<Person, Integer> {
    @Query
    Person findByName(String name);
}
