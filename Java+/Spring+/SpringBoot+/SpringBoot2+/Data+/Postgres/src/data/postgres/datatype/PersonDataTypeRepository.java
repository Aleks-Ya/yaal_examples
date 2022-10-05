package data.postgres.datatype;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface PersonDataTypeRepository extends CrudRepository<Person, Integer> {
    @Query
    Person findByName(String name);
}
