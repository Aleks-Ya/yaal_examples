import domain.NameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringHibernateTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void save() {
        NameEntity name = new NameEntity();
        name.setTitle("Boris");
        Session s = sessionFactory.openSession();
        s.save(name);
        assertEquals(1, (int) name.getId());
    }
}