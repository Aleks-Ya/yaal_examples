package jpa.test_config.data_jpa_test;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);
}
