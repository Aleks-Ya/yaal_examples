package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VtbBrokerLoginTest {

    @Test
    void loginToVtb() {
        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();
        assertNotNull(auth.getAuthCookie());
        assertNotNull(auth.getSlbCookie());
    }

    @Test
    void notAuthorizedException() throws IOException {
        var file = Files.createTempFile(getClass().getSimpleName(), ".properties").toFile();
        var config = new Config(file);
        config.setUsername("wrong-login");
        config.setPassword("wrong-password");
        var login = new VtbBrokerLogin(config);
        assertThrows(VtbBrokerLogin.NotAuthorizedException.class, login::login);
    }


}
