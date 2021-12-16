package jpa.delete.soft.access_to_deleted;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static jpa.delete.soft.access_to_deleted.Person.FILTER;
import static jpa.delete.soft.access_to_deleted.Person.IS_DELETED_PARAM;

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
        var filter = session.enableFilter(FILTER);
        filter.setParameter(IS_DELETED_PARAM, isDeleted);
        var products = personRepository.findAll();
        session.disableFilter(FILTER);
        return products;
    }
}
