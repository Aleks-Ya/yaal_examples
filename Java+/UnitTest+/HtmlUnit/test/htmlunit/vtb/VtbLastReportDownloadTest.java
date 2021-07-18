package htmlunit.vtb;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

class VtbLastReportDownloadTest {

    @Test
    void loadClientList() throws IOException {
        var vtbLogin = System.getProperty("vtb_login");
        var vtbPassword = System.getProperty("vtb_password");
        var login = new VtbBrokerLogin(vtbLogin, vtbPassword);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var outDir = Files.createTempDirectory(getClass().getSimpleName() + "_");
        for (String agreement : agreementData.getAgreements()) {
            var waiter = new VtbLastReportDownload(auth, agreement);
            var reportFile = waiter.downloadLastReport();
            var outputFile = outDir.resolve(reportFile.getFilename()).toFile();
            FileUtils.writeByteArrayToFile(outputFile, reportFile.getContent());
            System.out.println("Saved to file: " + outputFile);
        }
    }

}
