import domain.NameEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringEntityManagerTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void save() {
        NameEntity name = new NameEntity();
        name.setTitle("Boris");
        em.persist(name);
        em.flush();
        assertEquals(1, (int) name.getId());
    }
}