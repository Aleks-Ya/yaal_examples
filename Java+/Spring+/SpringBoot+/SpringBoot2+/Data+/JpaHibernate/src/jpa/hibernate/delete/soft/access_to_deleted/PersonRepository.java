package jpa.hibernate.delete.soft.access_to_deleted;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Integer> {
}
