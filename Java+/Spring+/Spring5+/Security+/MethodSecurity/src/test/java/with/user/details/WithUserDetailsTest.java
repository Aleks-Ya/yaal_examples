package with.user.details;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static with.user.details.TestConfig.USER_DETAILS_SERVICE_BEAN;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class WithUserDetailsTest {

    @Autowired
    private MessageService messageService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        messageService.getMessage();
    }

    @Test
    @WithUserDetails(value = TestConfig.USERNAME, userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN)
    public void getMessage() {
        assertThat(messageService.getMessage(), startsWith("Hello"));
    }
}
