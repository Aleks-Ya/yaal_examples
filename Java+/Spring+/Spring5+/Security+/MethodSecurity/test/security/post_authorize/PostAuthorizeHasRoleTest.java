package security.post_authorize;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Use @{@link org.springframework.security.access.prepost.PreAuthorize} annotation ("hasRole").
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class PostAuthorizeHasRoleTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageUnauthenticated() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.getMessage("abc"));
    }

    @Test
    @WithMockUser(roles = "SENDER")
    void getMessageNoRole() {
        assertThrows(AccessDeniedException.class, () -> messageService.getMessage("abc"));
    }

    @Test
    @WithMockUser(username = "John", roles = "RECEIVER")
    void getMessageWrongRecipient() {
        assertThrows(AccessDeniedException.class, () -> messageService.getMessage("Mark"));
    }

    @Test
    @WithMockUser(username = "John", roles = "RECEIVER")
    void getMessageWithMockUser() {
        assertThat(messageService.getMessage("John"))
                .isEqualTo(new Message("John", "Hello, John"));
    }
}
