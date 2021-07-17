package htmlunit;

import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

class VtbBrokerLogin {
    private final String username;
    private final String password;

    VtbBrokerLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    AuthData login() {
        try (var webClient = new WebClient()) {
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            webClient.setIncorrectnessListener((message, origin) -> {
            });

            HtmlPage page = webClient.getPage("https://lk.olb.ru");
            var forms = page.getForms();
            if (forms.size() != 1) {
                throw new LoginException("Expected 1 form, but found " + forms.size());
            }
            var loginForm = forms.get(0);
            var usernameInput = loginForm.getInputByName("UserName");
            var passwordInput = loginForm.getInputByName("Password");
            var submitInputs = loginForm.getByXPath("//input[@type='submit'][@class='save_button']");
            if (submitInputs.size() != 1) {
                throw new LoginException("Expected 1 submit button, but found " + submitInputs.size());
            }
            var submitInput = (HtmlSubmitInput) submitInputs.get(0);
            usernameInput.type(username);
            passwordInput.type(password);
            submitInput.click();
            var cookieManager = webClient.getCookieManager();
            var vtbAuthCookie = cookieManager.getCookie(".vtb-auth");
            if (vtbAuthCookie == null) {
                throw new NotAuthorizedException();
            }
            var aspSessionIdCookie = cookieManager.getCookie("ASP.NET_SessionId");
            var slbCookie = cookieManager.getCookie("slb");
            return new AuthData(vtbAuthCookie.getValue(), aspSessionIdCookie.getValue(), slbCookie.getValue());
        } catch (LoginException e) {
            throw e;
        } catch (Exception e) {
            throw new LoginException(e);
        }
    }

    static class AuthData {
        private final String vtbAuthCookie;
        private final String aspSessionIdCookie;
        private final String slbCookie;

        AuthData(String vtbAuthCookie, String aspSessionIdCookie, String slbCookie) {
            this.vtbAuthCookie = vtbAuthCookie;
            this.aspSessionIdCookie = aspSessionIdCookie;
            this.slbCookie = slbCookie;
        }

        public String getSlbCookie() {
            return slbCookie;
        }

        public String getAspSessionIdCookie() {
            return aspSessionIdCookie;
        }

        public String getVtbAuthCookie() {
            return vtbAuthCookie;
        }
    }

    static class LoginException extends RuntimeException {
        public LoginException(Throwable cause) {
            super(cause);
        }

        public LoginException(String message) {
            super(message);
        }
    }

    static class NotAuthorizedException extends LoginException {
        public NotAuthorizedException() {
            super("User was not authorized");
        }
    }

}
