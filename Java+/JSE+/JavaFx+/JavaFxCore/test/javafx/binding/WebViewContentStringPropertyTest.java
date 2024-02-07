package javafx.binding;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.testfx.api.FxAssert.verifyThat;

class WebViewContentStringPropertyTest extends ApplicationTest {
    private WebViewContentStringProperty webViewProperty;
    private StringProperty textAreaProperty;

    @Override
    public void start(Stage stage) {
        var webView = webView();
        var textArea = new TextArea("Text Area 1");
        webViewProperty = new WebViewContentStringProperty(webView);
        textAreaProperty = textArea.textProperty();
        textAreaProperty.bindBidirectional(webViewProperty);
        var scene = new Scene(new VBox(webView, textArea), 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    private static WebView webView() {
        var webView = new WebView();
        webView.setId("WebView");
        return webView;
    }

    private String getWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    @Test
    void modifyTextByProperty_FxThread() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        var webView1 = lookup("#WebView").queryAs(WebView.class);
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("<html><head></head><body></body></html>");
        interact(() -> verifyThat(webView1, webView -> getWebViewContent(webView)
                .equals("<html><head></head><body></body></html>")));
        Platform.runLater(() -> textAreaProperty.set("Text 2"));
        await().untilAsserted(() -> assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text 2"));
        interact(() -> verifyThat(webView1, webView -> getWebViewContent(webView)
                .equals("<html><head></head><body>Text 2</body></html>")));
        assertThat(webViewProperty.get()).isEqualTo("<html><head></head><body>Text 2</body></html>");
    }

    @Test
    void modifyTextByProperty_otherThread() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        var webView1 = lookup("#WebView").queryAs(WebView.class);
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("<html><head></head><body></body></html>");
        interact(() -> verifyThat(webView1, webView -> getWebViewContent(webView)
                .equals("<html><head></head><body></body></html>")));
        textAreaProperty.set("Text 2");
        await().untilAsserted(() -> assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text 2"));
        interact(() -> verifyThat(webView1, webView -> getWebViewContent(webView)
                .equals("<html><head></head><body>Text 2</body></html>")));
        assertThat(webViewProperty.get()).isEqualTo("<html><head></head><body>Text 2</body></html>");
    }
}
