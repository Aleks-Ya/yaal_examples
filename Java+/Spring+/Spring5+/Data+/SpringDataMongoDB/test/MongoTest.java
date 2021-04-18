import data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import repository.UserRepository;
import spring.TestConfiguration;

/**
 * @author Yablokov Aleksey
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class MongoTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void test() {
        repo.insert(new User("admin"));
    }
}
