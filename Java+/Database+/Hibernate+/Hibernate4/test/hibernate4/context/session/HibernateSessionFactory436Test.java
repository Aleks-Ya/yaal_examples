package hibernate4.context.session;

import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

class HibernateSessionFactory436Test {

    private static final HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(House.class);

    @Test
    void getSessionFactory() {
        var sf = factory.getSessionFactory();
        var s = sf.openSession();
        s.save(new House(1, "Spb"));
        s.flush();
        s.close();
    }

    @Test
    void openSession() {
        var s = factory.openSession();
        s.save(new House(2, "Spb"));
        s.flush();
        s.close();
    }
}

@Entity
class House {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String address;

    House(int id, String address) {
        this.id = id;
        this.address = address;
    }
}