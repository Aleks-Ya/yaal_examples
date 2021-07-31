package htmlunit.vtb;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class VtbBrokerReportRequestTest {

    @Test
    void requestReport() {
        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var periodTo = LocalDate.now().minusDays(1);
        var reportRequest = new VtbBrokerReportRequest(auth, agreementData, periodTo);
        reportRequest.sendReportRequest();
    }

}
