package liquibase.entity.datatype;

import org.springframework.data.repository.CrudRepository;

interface PersonRepository extends CrudRepository<Person, Long> {
}
