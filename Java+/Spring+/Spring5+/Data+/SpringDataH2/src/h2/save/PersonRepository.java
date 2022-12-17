package h2.save;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Long> {
}
