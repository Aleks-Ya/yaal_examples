package with.mock.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * WithMockUser on methods
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class WithMockUserOnMethodTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageUnauthenticated() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.getMessage());
    }

    @Test
    @WithMockUser
    void getMessageWithMockUser() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, user");
    }

    @Test
    @WithMockUser("customUsername")
    void getMessageWithMockUserCustomUsername() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, customUsername");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getMessageWithMockUserCustomUser() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, admin");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void getMessageWithMockUserCustomAuthorities() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, admin");
    }
}
