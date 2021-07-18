package htmlunit.vtb;

import org.junit.jupiter.api.Test;

class VtbBrokerReportRequestTest {

    @Test
    void requestReport() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var reportRequest = new VtbBrokerReportRequest(auth, agreementData);
        reportRequest.sendReportRequest();
    }

}
