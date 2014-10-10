import factory.HibernateSessionFactory436;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class HibernateSessionFactory436Test {

    private static HibernateSessionFactory436 factory = HibernateSessionFactory436.makeFactory(House.class);

    @Test
    public void getSessionFactory() throws Exception {
        SessionFactory sf = factory.getSessionFactory();
        Session s = sf.openSession();
        s.save(new House(1, "Spb"));
        s.flush();
        s.close();
    }

    @Test
    public void openSession() throws Exception {
        Session s = factory.openSession();
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