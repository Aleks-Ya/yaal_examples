package htmlunit.vtb;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class VtbBrokerReportRequest {
    private final AuthData authData;
    private final AgreementData agreementData;

    VtbBrokerReportRequest(AuthData authData, AgreementData agreementData) {
        this.authData = authData;
        this.agreementData = agreementData;
    }

    void sendReportRequest() {
        System.out.println("Requesting reports...");
        var uri = URI.create("https://lk.olb.ru/Reports/Broker");
        var httpClient = HttpClient.newBuilder().build();
        try {
            var clientCodes = agreementData.getAgreements();
            for (String clientCode : clientCodes) {
                System.out.printf("Requesting report for '%s'...\n", clientCode);
                var authCookie = ".vtb-auth" + "=" + authData.getVtbAuthCookie();
                var aspCookie = "ASP.NET_SessionId" + "=" + authData.getAspSessionIdCookie();
                var slbCookie = "slb" + "=" + authData.getSlbCookie();
                var body = "PeriodType=year&PeriodFrom=01.01.2021&PeriodTo=16.07.2021&Period_day=16.07.2021&startMonth=%D0%B8%D1%8E%D0%BB%2C+2021&startYear=2021&Period=01.01.2021-16.07.2021&fClientCode=#client_code#&ReportFormat=xls"
                        .replace("#client_code#", clientCode);
                var request = HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Cookie", authCookie + "; " + aspCookie + "; " + slbCookie)
                        .header("Accept", "*/*")
                        .header("Accept-Language", "en-US,en;q=0.5")
                        .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Origin", "https://lk.olb.ru")
                        .header("Referer", "https://lk.olb.ru/Reports/Broker")
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
