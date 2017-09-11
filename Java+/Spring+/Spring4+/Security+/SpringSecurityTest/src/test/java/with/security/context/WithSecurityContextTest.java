package with.security.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class WithSecurityContextTest {

    @Autowired
    private MessageService messageService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        messageService.getMessage();
    }

    @Test
    @WithMockCustomUser
    public void getMessage() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }
}
