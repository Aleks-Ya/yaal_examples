package redisspringboot.embedded;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test a Redis repository.
 *
 * @see LocalApp
 */
@SpringBootTest(classes = TestRedisConfiguration.class,
        properties = {"spring.redis.host=localhost", "spring.redis.port=6360"})
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    void findById() {
        var name = "John";
        var id = 1L;
        repo.save(new User(id, name));
        var actPerson = repo.findById(id);
        assertThat(actPerson).contains(new User(id, name));
    }
}
