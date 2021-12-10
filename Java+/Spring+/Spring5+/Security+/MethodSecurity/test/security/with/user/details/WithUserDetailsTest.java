package security.with.user.details;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static security.with.user.details.TestConfig.USER_DETAILS_SERVICE_BEAN;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class WithUserDetailsTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageUnauthenticated() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.getMessage());
    }

    @Test
    @WithUserDetails(value = TestConfig.USERNAME, userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN)
    void getMessage() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, " + TestConfig.USERNAME);
    }
}
