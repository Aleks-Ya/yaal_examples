package javafx.controls;

import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static javafx.concurrent.Worker.State.READY;
import static javafx.concurrent.Worker.State.RUNNING;
import static javafx.concurrent.Worker.State.SCHEDULED;
import static javafx.concurrent.Worker.State.SUCCEEDED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.testfx.api.FxAssert.verifyThat;

class WebViewTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var webView1 = webView1();
        var webView2 = webView2();
        var button = new Button("Load content");
        button.setId("Button");
        button.setOnAction(event -> webView1.getEngine().loadContent("New content"));
        var scene = new Scene(new VBox(webView1, button, webView2), 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    private static WebView webView1() {
        var content = "Original content";
        var webView = new WebView();
        webView.setId("WebView");
        webView.getEngine().loadContent(content);
        return webView;
    }

    private static WebView webView2() {
        var webView = new WebView();
        webView.getEngine().loadContent("WebView2 content");
        return webView;
    }

    private String getWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    @Test
    void shouldContainWebView() {
        interact(() -> verifyThat(lookup("#WebView").queryAs(WebView.class),
                webView -> getWebViewContent(webView).equals("<html><head></head><body>Original content</body></html>")));
    }

    @Test
    void getWebViewByStyleClass() {
        interact(() -> verifyThat(lookup(".web-view").queryAs(WebView.class),
                webView -> getWebViewContent(webView).equals("<html><head></head><body>Original content</body></html>")));
    }

    @Test
    void getAllWebViews() {
        interact(() -> assertThat(lookup(".web-view").queryAllAs(WebView.class)).hasSize(2));
    }

    @Test
    void shouldLoadContentToWebView() {
        clickOn("#Button");
        interact(() -> verifyThat(lookup("#WebView").queryAs(WebView.class),
                webView -> getWebViewContent(webView).equals("<html><head></head><body>New content</body></html>")));
    }

    @Test
    void workerStateListener() {
        var webView = lookup("#WebView").queryAs(WebView.class);
        var stateHistory = new ArrayList<State>();
        Platform.runLater(() -> {
            webView.getEngine().getLoadWorker().stateProperty()
                    .addListener((observable, oldValue, newValue) -> stateHistory.add(newValue));
            webView.getEngine().loadContent("new content");
        });
        await().untilAsserted(() -> assertThat(stateHistory).containsExactly(READY, SCHEDULED, RUNNING, SUCCEEDED));
    }
}
