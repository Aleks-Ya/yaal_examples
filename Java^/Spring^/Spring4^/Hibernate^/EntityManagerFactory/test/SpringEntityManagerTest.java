import domain.NameEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringEntityManagerTest {

    @Autowired
    private EntityManagerFactory factory;

    @Test
    public void save() {
        NameEntity name = new NameEntity();
        name.setTitle("Boris");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(name);
        em.flush();
        em.getTransaction().commit();
        assertEquals(1, (int) name.getId());
    }
}