import domain.NameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class SpringHibernateTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void save() {
        NameEntity name = new NameEntity();
        name.setTitle("Boris");
        Session s = sessionFactory.openSession();
        s.save(name);
        assertThat((int) name.getId()).isEqualTo(1);
    }
}