package htmlunit.vtb;

import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

import static htmlunit.vtb.Constants.AUTH_COOKIE;
import static htmlunit.vtb.Constants.SLB_COOKIE;
import static htmlunit.vtb.Constants.VTB_DOMAIN;

class WebClientFactory {
    static WebClient create() {
        var webClient = new WebClient();
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.setIncorrectnessListener((message, origin) -> {
        });
        return webClient;
    }

    static WebClient create(AuthData authData) {
        var webClient = create();
        var cookieManager = webClient.getCookieManager();
        cookieManager.addCookie(new Cookie(VTB_DOMAIN, AUTH_COOKIE, authData.getAuthCookie(), "/", -1, true));
        cookieManager.addCookie(new Cookie(VTB_DOMAIN, SLB_COOKIE, authData.getSlbCookie()));
        return webClient;
    }
}
