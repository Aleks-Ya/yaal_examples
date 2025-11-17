package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewApp extends Application {

    @Override
    public void start(Stage stage) {
        var webView1 = fullHtmlDocument();
        var webView2 = partialHtmlDocument();
        var element = copyHtmlButton();
        var size = webViewSize();
        var ctrlAltDownHotkey = disableCtrlAltDownHotkey();
        var vBox = new VBox(webView1, new Separator(), webView2, new Separator(), element, new Separator(),
                size, new Separator(), ctrlAltDownHotkey);
        var scene = new Scene(vBox, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    private static WebView partialHtmlDocument() {
        var content = "<h1>This is a Heading</h1>"
                + "<p>This is a paragraph.</p>";
        var webView = new WebView();
        webView.getEngine().loadContent(content);
        return webView;
    }

    private static WebView fullHtmlDocument() {
        var content = "<html><body>"
                + "<h1>This is a Heading</h1>"
                + "<p>This is a paragraph.</p>"
                + "</body></html>";
        var webView = new WebView();
        webView.getEngine().loadContent(content);
        return webView;
    }

    private static HBox copyHtmlButton() {
        var htmlContent = "<html><body>"
                + "<h1>This is a Heading</h1>"
                + "<p>This is a paragraph.</p>"
                + "</body></html>";
        var webView = new WebView();
        webView.getEngine().loadContent(htmlContent);
        var button = new Button("Copy HTML");
        button.setMinWidth(100);
        button.setOnAction(_ -> {
            var clipboard = Clipboard.getSystemClipboard();
            var clipboardContent = new ClipboardContent();
            var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
            clipboardContent.putHtml(content);
            clipboard.setContent(clipboardContent);
        });
        return new HBox(webView, button);
    }

    private static WebView webViewSize() {
        int minHeight = 50;
        int maxHeight = 200;
        var content = String.format("<p>Min height: %d<p/>"
                + "<p>Max height: %d</p>", minHeight, maxHeight);
        var webView = new WebView();
        webView.setMinHeight(minHeight);
        webView.setMaxHeight(maxHeight);
        webView.getEngine().loadContent(content);
        return webView;
    }

    private static WebView disableCtrlAltDownHotkey() {
        var content = "<html><body>"
                + "<p>This is a paragraph.</p>".repeat(10)
                + "</body></html>";
        var webView = new WebView();
        webView.setMinHeight(100);
        webView.setMaxHeight(100);
        webView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.isAltDown() && event.getCode() == KeyCode.DOWN) {
                event.consume();
            }
        });
        webView.getEngine().loadContent(content);
        return webView;
    }

}