package htmlunit;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class VtbBrokerClientListTest {

    @Test
    void loadClientList() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();
        assertThat(agreementData.getAgreements(), containsInAnyOrder("710H6840", "75529790", "75529792", "75529791"));
    }

}
