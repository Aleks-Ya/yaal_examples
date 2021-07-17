package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VtbBrokerLoginTest {

    @Test
    void loginToVtb() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();
        assertNotNull(auth.getVtbAuthCookie());
        assertNotNull(auth.getAspSessionIdCookie());
        assertNotNull(auth.getSlbCookie());
    }

    @Test
    void notAuthorizedException() {
        var login = new VtbBrokerLogin("wrong-login", "wrong-password");
        assertThrows(VtbBrokerLogin.NotAuthorizedException.class, login::login);
    }


}
