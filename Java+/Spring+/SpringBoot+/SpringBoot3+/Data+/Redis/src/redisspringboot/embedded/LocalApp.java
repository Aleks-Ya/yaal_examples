package redisspringboot.embedded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.RandomUtil;

/**
 * SpringBoot application connects to a local Redis server.
 */
@SpringBootApplication
public class LocalApp {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(LocalApp.class, args);
        var userRepo = ctx.getBean(UserRepository.class);
        var id = RandomUtil.randomLongPositive();
        var expUser = new User(id, "John");
        userRepo.save(expUser);
        var actUser = userRepo.findById(id).orElseThrow();
        assert actUser.equals(expUser);
    }
}
