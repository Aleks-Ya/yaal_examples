package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import static htmlunit.vtb.Constants.AUTH_COOKIE;
import static htmlunit.vtb.Constants.SLB_COOKIE;
import static htmlunit.vtb.Constants.VTB_BASE_URL;

class VtbBrokerLogin {
    private final String username;
    private final String password;

    VtbBrokerLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    AuthData login() {
        System.out.println("Logging in...");
        try (var webClient = WebClientFactory.create()) {
            HtmlPage page = webClient.getPage(VTB_BASE_URL);
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
            var vtbAuthCookie = cookieManager.getCookie(AUTH_COOKIE);
            if (vtbAuthCookie == null) {
                throw new NotAuthorizedException();
            }
            var slbCookie = cookieManager.getCookie(SLB_COOKIE);
            var authData = new AuthData(vtbAuthCookie.getValue(), slbCookie.getValue());
            System.out.println("Logged in.");
            return authData;
        } catch (LoginException e) {
            throw e;
        } catch (Exception e) {
            throw new LoginException(e);
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
