import domain.NameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Одна встроенная БД.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
class SpringEntityManagerTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void save() {
        var name = new NameEntity();
        name.setTitle("Boris");
        em.persist(name);
        em.flush();
        assertThat((int) name.getId()).isEqualTo(1);
    }
}