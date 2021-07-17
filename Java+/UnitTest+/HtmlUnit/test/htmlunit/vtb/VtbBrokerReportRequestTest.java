package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class VtbBrokerReportRequestTest {

    @Test
    void requestReport() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

//        var auth = new AuthData("");

        var reportRequest = new VtbBrokerReportRequest(auth, agreementData);
        reportRequest.sendReportRequest();
        assertThat(agreementData.getAgreements(), containsInAnyOrder("710H6840", "75529790", "75529792", "75529791"));
    }

}
