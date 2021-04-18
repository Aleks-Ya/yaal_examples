import domain.NameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Одна встроенная БД.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
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