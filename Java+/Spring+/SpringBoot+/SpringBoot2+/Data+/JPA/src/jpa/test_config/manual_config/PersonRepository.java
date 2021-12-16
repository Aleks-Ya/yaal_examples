package jpa.test_config.manual_config;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);
}
