package htmlunit.vtb;

import org.junit.jupiter.api.Test;

class VtbAllReportsFinishedWaitTest {

    @Test
    void loadClientList() {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        for (String agreement : agreementData.getAgreements()) {
            var waiter = new VtbAllReportsFinishedWait(auth, agreement);
            waiter.waitUntilFinished();
        }
    }

}
