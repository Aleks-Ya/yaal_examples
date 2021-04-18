import data.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.UserRepository;
import spring.TestConfiguration;

import java.io.IOException;

/**
 * @author Yablokov Aleksey
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class MongoTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void test() throws IOException {
        repo.insert(new User("admin"));
    }
}
