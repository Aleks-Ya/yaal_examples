package hibernate5.context.session;

import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

class HibernateSessionFactory5Test {

    private static final HibernateSessionFactory5 factory = HibernateSessionFactory5.makeFactory(House.class);

    @Test
    void getSessionFactory() {
        var sf = factory.getSessionFactory();
        var s = sf.openSession();
        s.beginTransaction();
        s.save(new House(1, "Spb"));
        s.flush();
        s.getTransaction().commit();
        s.close();
    }

    @Test
    void openSession() {
        var s = factory.openSession();
        s.beginTransaction();
        s.save(new House(2, "Spb"));
        s.flush();
        s.getTransaction().commit();
        s.close();
    }
}

@Entity
class House {
    @Id
    @GeneratedValue
    private final int id;

    @Column
    private final String address;

    House() {
        this(1, null);
    }

    House(int id, String address) {
        this.id = id;
        this.address = address;
    }

}