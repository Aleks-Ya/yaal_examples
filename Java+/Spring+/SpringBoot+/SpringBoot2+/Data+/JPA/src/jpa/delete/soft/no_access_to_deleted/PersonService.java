package jpa.delete.soft.no_access_to_deleted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person create(Person product) {
        return personRepository.save(product);
    }

    public void remove(Integer id) {
        personRepository.deleteById(id);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
}
