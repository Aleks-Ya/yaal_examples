package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewApp extends Application {
    @Override
    public void start(Stage stage) {
        var htmlContent = "<html><body>"
                + "<h1>This is a Heading</h1>"
                + "<p>This is a paragraph.</p>"
                + "</body></html>";
        var webView = new WebView();
        webView.getEngine().loadContent(htmlContent);
        var scene = new Scene(new StackPane(webView), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}