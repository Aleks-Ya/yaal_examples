package hibernate5.mapping.assosiation.unidirectional;

import hibernate5.context.session.HibernateSessionFactory5;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PeopleTest {
    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(
            People.class, Address.class);

    @Test
    void test() {
        saveEntities();
        readEntities();
    }

    private void saveEntities() {
        try (var session = factory.openSession()) {
            session.beginTransaction();

            var man = new People();
            var woman = new People();
            Set<People> peoples = new HashSet<>();
            peoples.add(man);
            peoples.add(woman);

            var spb = new Address();
            var moscow = new Address();

            moscow.setPeoples(peoples);
            spb.setPeoples(peoples);

            session.save(man);
            session.save(woman);
            session.save(moscow);
            session.save(spb);
            session.flush();
            session.getTransaction().commit();
        }
    }

    private void readEntities() {
        var session = factory.openSession();

        List<People> allPeoples = session.createCriteria(People.class).list();
        final var expPeopleSize = 2;
        assertThat(allPeoples).hasSize(expPeopleSize);

        List<Address> allAddresses = session.createCriteria(Address.class).list();
        for (var address : allAddresses) {
            assertThat(address.getPeoples()).hasSize(expPeopleSize);
        }

        session.close();
    }
}
