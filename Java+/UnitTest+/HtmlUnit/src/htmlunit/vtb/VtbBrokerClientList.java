package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.util.List;
import java.util.stream.Collectors;

class VtbBrokerClientList {
    private final AuthData authData;

    VtbBrokerClientList(AuthData authData) {
        this.authData = authData;
    }

    AgreementData clientList() {
        try (var webClient = new WebClient()) {
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            webClient.setIncorrectnessListener((message, origin) -> {
            });

            var cookieManager = webClient.getCookieManager();
            var domain = "lk.olb.ru";
            cookieManager.addCookie(new Cookie(domain, ".vtb-auth", authData.getVtbAuthCookie(), "/", -1, true));
            cookieManager.addCookie(new Cookie(domain, "ASP.NET_SessionId", authData.getAspSessionIdCookie()));
            cookieManager.addCookie(new Cookie(domain, "slb", authData.getSlbCookie()));
            HtmlPage page = webClient.getPage("https://lk.olb.ru/Reports/Broker");
            var clientCode = (HtmlSelect) page.getElementById("ClientCode");
            var options = clientCode.getOptions().stream()
                    .map(HtmlOption::getValueAttribute)
                    .collect(Collectors.toList());
            return new AgreementData(options);
        } catch (Exception e) {
            throw new ClientListException(e);
        }
    }

    static class AgreementData {
        private final List<String> agreements;

        AgreementData(List<String> agreements) {
            this.agreements = agreements;
        }

        public List<String> getAgreements() {
            return agreements;
        }
    }

    static class ClientListException extends RuntimeException {
        public ClientListException(Throwable cause) {
            super(cause);
        }

        public ClientListException(String message) {
            super(message);
        }
    }

}
