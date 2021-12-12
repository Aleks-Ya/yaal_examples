package mongo;

import mongo.data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import mongo.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
class MongoTest {
    @Autowired
    private UserRepository repo;

    @Test
    void test() {
        repo.insert(new User("admin"));
    }
}
