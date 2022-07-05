package hibernate4.mapping.assosiation.unidirectional;

import hibernate4.context.session.HibernateSessionFactory436;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PeopleTest {
    private static final HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(
            People.class, Address.class);

    @Test
    void test() throws Exception {
        saveEntities();
        readEntities();
    }

    private void saveEntities() {
        Session session = null;
        try {
            session = factory.openSession();
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
            session.getTransaction().commit();
            session.flush();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void readEntities() throws Exception {
        var session = factory.openSession();

        List<People> allPeoples = session.createCriteria(People.class).list();
        final var expPeopleSize = 2;
        assertThat(allPeoples.size()).isEqualTo(expPeopleSize);

        List<Address> allAddresses = session.createCriteria(Address.class).list();
        for (var address : allAddresses) {
            assertThat(address.getPeoples().size()).isEqualTo(expPeopleSize);
        }

        session.close();
    }
}
