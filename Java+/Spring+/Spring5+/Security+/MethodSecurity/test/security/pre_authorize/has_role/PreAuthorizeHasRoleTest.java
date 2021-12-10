package security.pre_authorize.has_role;

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
class PreAuthorizeHasRoleTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageUnauthenticated() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.getMessage());
    }

    @Test
    @WithMockUser(roles = "SENDER")
    void getMessageNoRole() {
        assertThrows(AccessDeniedException.class, () -> messageService.getMessage());
    }

    @Test
    @WithMockUser(roles = "RECEIVER")
    void getMessageWithMockUser() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, user");
    }
}
