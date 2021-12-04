package htmlunit.vtb;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

/**
 * Config ~/.vtb-broker-report-downloader/config.properties:
 * username=...
 * password=...
 */
class VtbBrokerReportDownloader {
    public static void main(String[] args) throws IOException {
        System.out.println("Downloading broker reports...");

        var config = new Config();
        var login = new VtbBrokerLogin(config);
        var auth = login.login();

        var clients = new VtbBrokerClientList(auth);
        var agreementData = clients.clientList();

        var periodTo = LocalDate.now().minusDays(1);
        var reportRequest = new VtbBrokerReportRequest(auth, agreementData, periodTo);
        reportRequest.sendReportRequest();

        for (String agreement : agreementData.getAgreements()) {
            var waiter = new VtbAllReportsFinishedWait(auth, agreement);
            waiter.waitUntilFinished();
        }

        var outDir = Files.createTempDirectory(VtbBrokerReportDownloader.class.getSimpleName() + "_");
        for (String agreement : agreementData.getAgreements()) {
            var waiter = new VtbLastReportDownload(auth, agreement);
            var reportFile = waiter.downloadLastReport();
            var outputFile = outDir.resolve(reportFile.getFilename()).toFile();
            FileUtils.writeByteArrayToFile(outputFile, reportFile.getContent());
            System.out.println("Saved to file: " + outputFile);
        }

        System.out.println("Broker reports are downloaded.");
    }
}
