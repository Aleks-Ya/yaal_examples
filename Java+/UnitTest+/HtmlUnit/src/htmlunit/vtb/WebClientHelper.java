package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static htmlunit.vtb.Constants.VTB_BASE_URL;
import static htmlunit.vtb.Constants.VTB_REPORTS_URL;
import static htmlunit.vtb.Constants.VTB_REPORT_LIST_URL;

class WebClientHelper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    static HtmlTable getReportTable(WebClient webClient, String agreement) throws IOException {
        var url = new URL(VTB_REPORT_LIST_URL);
        var request = new WebRequest(url, HttpMethod.POST);
        request.setAdditionalHeader("Accept", "*/*");
        request.setAdditionalHeader("Accept-Language", "en-US,en;q=0.5");
        request.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.setAdditionalHeader("Referer", VTB_REPORTS_URL);
        request.setAdditionalHeader("Origin", VTB_BASE_URL);
        request.setAdditionalHeader("Sec-Fetch-Dest", "empty");
        request.setAdditionalHeader("Sec-Fetch-Mode", "cors");
        request.setAdditionalHeader("Sec-Fetch-Site", "same-origin");
        request.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");

        var startDate = LocalDate.now().withDayOfMonth(1).withMonth(1).format(formatter);
        var endDate = LocalDate.now().withDayOfMonth(31).withMonth(12).format(formatter);
        var body = String.format("StartDate=%s&EndDate=%s&ClientCode=%s&AgreementNumber=10H684",
                startDate, endDate, agreement);
        request.setRequestBody(body);

        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = webClient.getPage(request);
        return (HtmlTable) page.getElementById("grid");
    }
}
