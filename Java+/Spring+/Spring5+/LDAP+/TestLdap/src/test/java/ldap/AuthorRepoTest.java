package ldap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ldap.config.TestLdapConfig;
import ldap.repository.PersonRepo;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestLdapConfig.class)
public class AuthorRepoTest {

    @Autowired
    private PersonRepo repo;

    @Test
    public void test() {
        List<String> allPersonNames = repo.getAllPersonNames();
        System.out.println("Persons: " + allPersonNames);
    }

}