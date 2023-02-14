package jpa.hibernate.delete.soft.access_to_deleted;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager entityManager;

    public Person create(Person product) {
        return personRepository.save(product);
    }

    public void remove(Integer id) {
        personRepository.deleteById(id);
    }

    public Iterable<Person> findAll(boolean isDeleted) {
        var session = entityManager.unwrap(Session.class);
        var filter = session.enableFilter(Person.FILTER);
        filter.setParameter(Person.IS_DELETED_PARAM, isDeleted);
        var products = personRepository.findAll();
        session.disableFilter(Person.FILTER);
        return products;
    }
}
