package htmlunit.vtb;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static htmlunit.vtb.Constants.AUTH_COOKIE;
import static htmlunit.vtb.Constants.SLB_COOKIE;
import static htmlunit.vtb.Constants.VTB_BASE_URL;
import static htmlunit.vtb.Constants.VTB_REPORTS_URL;

class VtbBrokerReportRequest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final AuthData authData;
    private final AgreementData agreementData;
    private final LocalDate periodTo;

    VtbBrokerReportRequest(AuthData authData, AgreementData agreementData, LocalDate periodTo) {
        this.authData = authData;
        this.agreementData = agreementData;
        this.periodTo = periodTo;
    }

    void sendReportRequest() {
        System.out.println("Requesting reports...");
        var uri = URI.create(VTB_REPORTS_URL);
        var httpClient = HttpClient.newBuilder().build();
        try {
            var clientCodes = agreementData.getAgreements();
            for (String clientCode : clientCodes) {
                System.out.printf("Requesting report for '%s'...\n", clientCode);
                var authCookie = AUTH_COOKIE + "=" + authData.getAuthCookie();
                var slbCookie = SLB_COOKIE + "=" + authData.getSlbCookie();
                var periodFrom = periodTo.withDayOfMonth(1).withMonth(1);
                var periodFromStr = periodFrom.format(formatter);
                var periodToStr = periodTo.format(formatter);
                var startYearStr = String.valueOf(periodTo.getYear());
                var body = "PeriodType=year&PeriodFrom=#period_from#&PeriodTo=#period_to#&Period_day=#period_to#&startMonth=%D0%B8%D1%8E%D0%BB%2C+2021&startYear=#start_year#&Period=#period_from#-#period_to#&fClientCode=#client_code#&ReportFormat=xls"
                        .replaceAll("#period_from#", periodFromStr)
                        .replaceAll("#period_to#", periodToStr)
                        .replaceAll("#start_year#", startYearStr)
                        .replaceAll("#client_code#", clientCode);
                var request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Cookie", authCookie + "; " + slbCookie)
                        .header("Accept", "*/*")
                        .header("Accept-Language", "en-US,en;q=0.5")
                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Origin", VTB_BASE_URL)
                        .header("Referer", VTB_REPORTS_URL)
                        .header("Sec-Fetch-Dest", "empty")
                        .header("Sec-Fetch-Mode", "cors")
                        .header("Sec-Fetch-Site", "same-origin")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
                var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                var actBody = response.body();
                if (!actBody.contains("Команда успешно выполнена.")) {
                    throw new RequestReportException("Not successful: " + actBody);
                }
                System.out.printf("Report requested for '%s'.\n", clientCode);
            }
            System.out.println("Reports requested.");
        } catch (Exception e) {
            throw new RequestReportException(e);
        }
    }

    static class RequestReportException extends RuntimeException {
        public RequestReportException(Throwable cause) {
            super(cause);
        }

        public RequestReportException(String message) {
            super(message);
        }
    }

}
