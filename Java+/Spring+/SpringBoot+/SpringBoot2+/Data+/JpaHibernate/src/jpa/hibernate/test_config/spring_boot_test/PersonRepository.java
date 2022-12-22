package jpa.hibernate.test_config.spring_boot_test;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);
}
