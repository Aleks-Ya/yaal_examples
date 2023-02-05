package htmlunit.vtb;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

class VtbLastReportDownloadTest {

    @Test
    void loadClientList() throws IOException {
        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var outDir = Files.createTempDirectory(getClass().getSimpleName() + "_");
        for (var agreement : agreementData.getAgreements()) {
            var waiter = new VtbLastReportDownload(auth, agreement);
            var reportFile = waiter.downloadLastReport();
            var outputFile = outDir.resolve(reportFile.getFilename()).toFile();
            FileUtils.writeByteArrayToFile(outputFile, reportFile.getContent());
            System.out.println("Saved to file: " + outputFile);
        }
    }

}
