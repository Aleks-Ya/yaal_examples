package jpa.hibernate.where;

import org.springframework.data.repository.CrudRepository;

interface AdultPersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);
}
