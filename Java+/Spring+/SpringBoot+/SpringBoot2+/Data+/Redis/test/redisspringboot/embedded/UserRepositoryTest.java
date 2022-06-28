package redisspringboot.embedded;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = {UserRepository.class})
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
