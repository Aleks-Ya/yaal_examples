package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.util.stream.Collectors;

import static htmlunit.vtb.Constants.AUTH_COOKIE;
import static htmlunit.vtb.Constants.SLB_COOKIE;
import static htmlunit.vtb.Constants.VTB_DOMAIN;
import static htmlunit.vtb.Constants.VTB_REPORTS_URL;

class VtbBrokerClientList {
    private final AuthData authData;

    VtbBrokerClientList(AuthData authData) {
        this.authData = authData;
    }

    AgreementData clientList() {
        System.out.println("Getting client list...");
        try (var webClient = new WebClient()) {
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            webClient.setIncorrectnessListener((message, origin) -> {
            });

            var cookieManager = webClient.getCookieManager();
            cookieManager.addCookie(new Cookie(VTB_DOMAIN, AUTH_COOKIE, authData.getAuthCookie(), "/", -1, true));
            cookieManager.addCookie(new Cookie(VTB_DOMAIN, SLB_COOKIE, authData.getSlbCookie()));
            HtmlPage page = webClient.getPage(VTB_REPORTS_URL);
            var clientCode = (HtmlSelect) page.getElementById("ClientCode");
            var options = clientCode.getOptions().stream()
                    .map(HtmlOption::getValueAttribute)
                    .collect(Collectors.toList());
            System.out.println("Got client list: " + options);
            return new AgreementData(options);
        } catch (Exception e) {
            throw new ClientListException(e);
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
