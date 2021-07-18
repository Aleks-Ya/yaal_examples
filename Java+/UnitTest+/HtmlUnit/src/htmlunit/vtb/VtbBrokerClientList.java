package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import java.util.stream.Collectors;

import static htmlunit.vtb.Constants.VTB_REPORTS_URL;

class VtbBrokerClientList {
    private final AuthData authData;

    VtbBrokerClientList(AuthData authData) {
        this.authData = authData;
    }

    AgreementData clientList() {
        System.out.println("Getting client list...");
        try (var webClient = WebClientFactory.create(authData)) {
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
    }

}
