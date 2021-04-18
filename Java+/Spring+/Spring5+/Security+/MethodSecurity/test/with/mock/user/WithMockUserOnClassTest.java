package with.mock.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * WithMockUser on class
 */
@RunWith(SpringRunner.class)
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
