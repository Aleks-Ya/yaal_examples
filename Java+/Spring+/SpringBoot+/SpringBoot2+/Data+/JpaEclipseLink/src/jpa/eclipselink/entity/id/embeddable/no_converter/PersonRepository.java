package jpa.eclipselink.entity.id.embeddable.no_converter;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, PersonId> {
    Person findByName(String name);
}
