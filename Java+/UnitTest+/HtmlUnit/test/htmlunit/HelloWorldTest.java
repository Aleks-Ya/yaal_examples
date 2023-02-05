package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloWorldTest {

    @Test
    void homePage() throws Exception {
        try (var webClient = new WebClient()) {
            HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
            assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

            var pageAsXml = page.asXml();
            assertTrue(pageAsXml.contains("<body class=\"topBarDisabled\">"));

            var pageAsText = page.asNormalizedText();
            assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }
    }

}
