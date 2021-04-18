package with.mock.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

/**
 * WithMockUser on class
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WithMockUser
public class WithMockUserOnClassTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void getMessageWithMockUser() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }
}
