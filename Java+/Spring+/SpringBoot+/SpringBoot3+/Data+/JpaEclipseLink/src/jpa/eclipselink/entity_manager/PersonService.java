package jpa.eclipselink.entity_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CollectionUtil;

import java.util.List;

@Service
class PersonService {
    @Autowired
    private PersonRepository repo;

    public Person savePerson(Person person) {
        return repo.save(person);
    }

    public List<Person> findAllPersons() {
        return CollectionUtil.iterableToList(repo.findAll());
    }

    public PersonRepository getRepo() {
        return repo;
    }
}
