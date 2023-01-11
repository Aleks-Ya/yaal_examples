package jpa.hibernate.entity.key.composit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Integer> {
    @Query
    Person findByName(String name);
}
