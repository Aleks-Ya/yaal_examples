package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import org.apache.commons.io.IOUtils;

class VtbLastReportDownload {
    private final AuthData authData;
    private final String agreement;

    VtbLastReportDownload(AuthData authData, String agreement) {
        this.authData = authData;
        this.agreement = agreement;
    }

    ReportFile downloadLastReport() {
        System.out.printf("Downloading the last report for %s...\n", agreement);
        try (var webClient = WebClientFactory.create(authData)) {
            var table = WebClientHelper.getReportTable(webClient, agreement);
            var lastRow = table.getRow(table.getRowCount() - 1);
            var allCells = lastRow.getCells();
            var downloadCell = allCells.get(allCells.size() - 1);
            var downloadLink = (HtmlAnchor) downloadCell.getElementsByTagName("a").get(0);
            var downloadPage = (UnexpectedPage) downloadLink.click();
            var filename = downloadPage.getWebResponse()
                    .getResponseHeaderValue("Content-Disposition")
                    .replace("inline; filename=", "");
            byte[] bytes;
            try (var is = downloadPage.getInputStream()) {
                bytes = IOUtils.toByteArray(is);
            }
            var report = new ReportFile(filename, bytes);
            System.out.println("The last report is downloaded.");
            return report;
        } catch (Exception e) {
            throw new LastReportDownloadException(e);
        }
    }

    static class LastReportDownloadException extends RuntimeException {
        public LastReportDownloadException(Throwable cause) {
            super(cause);
        }
    }

    static class ReportFile {
        private final String filename;
        private final byte[] content;

        ReportFile(String filename, byte[] content) {
            this.filename = filename;
            this.content = content;
        }

        public String getFilename() {
            return filename;
        }

        public byte[] getContent() {
            return content;
        }
    }

}
