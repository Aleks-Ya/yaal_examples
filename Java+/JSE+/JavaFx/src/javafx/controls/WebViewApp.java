package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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
        var scene = new Scene(new VBox(webView1, webView2, element), 640, 480);
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
        button.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var clipboardContent = new ClipboardContent();
            var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
            clipboardContent.putHtml(content);
            clipboard.setContent(clipboardContent);
        });
        return new HBox(webView, button);
    }

    public static void main(String[] args) {
        launch();
    }
}