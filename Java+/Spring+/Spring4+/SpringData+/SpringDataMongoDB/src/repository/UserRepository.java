package repository;

import data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByName(String name);
}
