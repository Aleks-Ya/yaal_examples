import domain.NameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Одна встроенная БД.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
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