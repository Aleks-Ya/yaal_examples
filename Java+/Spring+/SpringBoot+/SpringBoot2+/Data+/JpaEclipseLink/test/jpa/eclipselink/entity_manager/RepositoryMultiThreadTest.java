package jpa.eclipselink.entity_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.Executors;

import static java.lang.Thread.currentThread;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Tuple4.of;

/**
 * Use {@link Service} and {@link Repository} from different threads.
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {Person.class, PersonRepository.class, PersonService.class})
class RepositoryMultiThreadTest {
    @Autowired
    private PersonService service;

    @Test
    void test() throws Exception {
        var t1 = Executors.newSingleThreadExecutor().submit(() -> {
            var savePerson = service.savePerson(new Person("John", 21, true));
            return of(currentThread().getName(), service, service.getRepo(), savePerson);
        }).get();

        var t2 = Executors.newSingleThreadExecutor()
                .submit(() -> of(currentThread().getName(), service, service.getRepo(), service.findAllPersons()))
                .get();

        assertThat(t2.element1()).isNotEqualTo(t1.element1());
        assertThat(t2.element2()).isEqualTo(t1.element2());
        assertThat(t2.element3()).isEqualTo(t1.element3());
        assertThat(t2.element4()).containsExactly(t1.element4());
    }
}
