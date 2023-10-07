package javafx.fxml_.mvc;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import static javafx.scene.input.KeyCode.C;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class MvcFXMLTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new MvcFXML().start(stage);
    }

    private String getWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    @Test
    void typeText() {
        var textArea = lookup("#textArea").queryAs(TextArea.class);
        var webView = lookup("#webView").queryAs(WebView.class);
        var button = lookup("#button1").queryButton();
        verifyThat(button, hasText("Reset text"));
        assertThat(textArea.getText()).isEmpty();
        interact(() -> assertThat(getWebViewContent(webView)).isEqualTo("<html><head></head><body></body></html>"));

        clickOn(textArea);
        type(A, B, C);
        assertThat(textArea.getText()).isEqualTo("abc");
        interact(() -> assertThat(getWebViewContent(webView)).isEqualTo("<html><head></head><body>abc</body></html>"));

        clickOn(button);
        assertThat(textArea.getText()).isEqualTo("Original text");
        interact(() -> assertThat(getWebViewContent(webView)).isEqualTo("<html><head></head><body>Original text</body></html>"));
    }

}