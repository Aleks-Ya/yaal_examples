package htmlunit.vtb;

import org.junit.jupiter.api.Test;

class VtbAllReportsFinishedWaitTest {

    @Test
    void loadClientList() {
        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        for (var agreement : agreementData.getAgreements()) {
            var waiter = new VtbAllReportsFinishedWait(auth, agreement);
            waiter.waitUntilFinished();
        }
    }

}
