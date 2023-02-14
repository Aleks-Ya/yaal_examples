package liquibase.repo;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Long> {
}
