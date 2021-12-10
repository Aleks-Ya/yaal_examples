package security.with.mock.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * WithMockUser on class
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WithMockUser
class WithMockUserOnClassTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageWithMockUser() {
        assertThat(messageService.getMessage()).isEqualTo("Hello, user");
    }
}
