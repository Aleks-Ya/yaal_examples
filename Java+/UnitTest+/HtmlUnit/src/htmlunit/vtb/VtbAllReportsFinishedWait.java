package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import java.util.ArrayList;

import static htmlunit.vtb.Constants.VTB_REPORTS_URL;

class VtbAllReportsFinishedWait {
    private final AuthData authData;
    private final String agreement;

    VtbAllReportsFinishedWait(AuthData authData, String agreement) {
        this.authData = authData;
        this.agreement = agreement;
    }

    void waitUntilFinished() {
        System.out.printf("Waiting all reports are finished for agreement %s...\n", agreement);
        try (var webClient = WebClientFactory.create(authData)) {
            HtmlPage page = webClient.getPage(VTB_REPORTS_URL);

            var getListForm = (HtmlForm) page.getElementById("getList");
            var clientCodeSelect = (HtmlSelect) getListForm.getElementsByAttribute("select", "id", "ClientCode").get(0);
            var option = clientCodeSelect.getOptionByValue(agreement);
            option.setSelected(true);
            var submitButton = (HtmlSubmitInput) getListForm.getElementsByAttribute("input", "type", "submit").get(0);
            HtmlPage actualPage = submitButton.click();
            var table = (HtmlTable) actualPage.getElementById("grid");
            var headerRow = table.getRow(0);
            var dataRows = new ArrayList<>(table.getRows());
            dataRows.remove(0);
            var reportCellIndex = headerRow.getCells().stream()
                    .filter(cell -> "Отчет".equalsIgnoreCase(cell.getFirstChild().getNodeValue()))
                    .findFirst().get().getIndex();
            for (HtmlTableRow row : dataRows) {
                var reportCell = row.getCell(reportCellIndex);
                var content = reportCell.getTextContent();
                if ("В обработке".equalsIgnoreCase(content)) {
                    System.out.println("Not ready row " + row.getIndex());
                } else if ("Отчет готов".equalsIgnoreCase(content)) {
                    System.out.println("Ready row " + row.getIndex());
                } else {
                    throw new WaitReportsException(String.format("Unknown %d row status: %s", row.getIndex(), content));
                }
            }
            System.out.println("All requests are finished.");
        } catch (Exception e) {
            throw new WaitReportsException(e);
        }
    }

    static class WaitReportsException extends RuntimeException {
        public WaitReportsException(Throwable cause) {
            super(cause);
        }

        public WaitReportsException(String message) {
            super(message);
        }
    }

}
