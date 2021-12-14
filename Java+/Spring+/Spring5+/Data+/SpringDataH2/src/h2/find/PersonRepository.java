package h2.find;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByName(String name);

    Optional<Person> findByNameAndAge(String name, Integer age);

    List<Person> findAllByName(String name);

    List<Person> findAllByNameOrderByAge(String name);

    List<Person> findAllByNameOrderByAgeAsc(String name);

    List<Person> findAllByNameOrderByAgeDesc(String name);

    List<Person> findAllByNameIn(List<String> names);

    List<Person> findByNameInAndAgeIn(List<String> names, List<Integer> ages);

    List<Person> findByMarriedTrue();

    List<Person> findByMarriedFalse();
}
