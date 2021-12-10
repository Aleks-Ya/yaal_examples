package security.pre_authorize.authenticated;

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
 * Use @{@link org.springframework.security.access.prepost.PreAuthorize} annotation ("authenticated").
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class PreAuthorizeAuthenticatedTest {

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
}
