package with.mock.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * WithMockUser on methods
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class WithMockUserOnMethodTest {

    @Autowired
    private MessageService messageService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        messageService.getMessage();
    }

    @Test
    @WithMockUser
    public void getMessageWithMockUser() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }

    @Test
    @WithMockUser("customUsername")
    public void getMessageWithMockUserCustomUsername() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void getMessageWithMockUserCustomUser() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void getMessageWithMockUserCustomAuthorities() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }
}
