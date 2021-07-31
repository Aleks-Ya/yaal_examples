package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class VtbBrokerReportRequestTest {

    @Test
    void requestReport() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var periodTo = LocalDate.now().minusDays(1);
        var reportRequest = new VtbBrokerReportRequest(auth, agreementData, periodTo);
        reportRequest.sendReportRequest();
    }

}
