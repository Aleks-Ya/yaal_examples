package security.authentication.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Using {@link TestingAuthenticationToken} and {@link TestingAuthenticationProvider}.
 */
@SpringBootTest(classes = {Config.class, SecuredService.class})
class TestingAuthenticationTest {
    @Autowired
    private SecuredService securedService;

    @Test
    void authorizedManually() {
        var token = new TestingAuthenticationToken("TestUser", "TestPassword", "MANAGER_ROLE");
        SecurityContextHolder.getContext().setAuthentication(token);
        var secretWord = securedService.getSecretWord();
        assertThat(secretWord).isEqualTo("peace");
    }

    @Test
    void unauthorizedManually() {
        var token = new TestingAuthenticationToken("TestUser", "TestPassword", "WRONG_ROLE");
        SecurityContextHolder.getContext().setAuthentication(token);
        assertThatThrownBy(() -> securedService.getSecretWord()).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void noTokenManually() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertThatThrownBy(() -> securedService.getSecretWord()).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    @WithMockUser(username = "TestUser", roles = "MANAGER_ROLE")
    void authorizedWithMockUser() {
        var secretWord = securedService.getSecretWord();
        assertThat(secretWord).isEqualTo("peace");
    }

    @Test
    @WithMockUser(username = "TestUser", roles = "WRONG_ROLE")
    void unauthorizedWithMockUser() {
        assertThatThrownBy(() -> securedService.getSecretWord()).isInstanceOf(AccessDeniedException.class);
    }
}