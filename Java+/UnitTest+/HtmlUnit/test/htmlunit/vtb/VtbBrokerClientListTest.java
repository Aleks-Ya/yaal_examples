package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class VtbBrokerClientListTest {

    @Test
    void loadClientList() {
        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();
        assertThat(agreementData.getAgreements(), containsInAnyOrder("710H6840", "75529790", "75529792", "75529791"));
    }

}
