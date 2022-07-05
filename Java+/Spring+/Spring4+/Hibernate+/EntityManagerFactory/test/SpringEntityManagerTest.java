import domain.NameEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class SpringEntityManagerTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void save() {
        NameEntity name = new NameEntity();
        name.setTitle("Boris");
        em.persist(name);
        em.flush();
        assertThat((int) name.getId()).isEqualTo(1);
    }
}