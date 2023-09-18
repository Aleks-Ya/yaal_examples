package gptui.ui.view;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

class AnswerPane extends HBox {
    static final String STATUS_CIRCLE_ID = "status-circle";
    private final WebView webView = new WebView();
    private final Circle statusCircle = new Circle();

    public AnswerPane(String header) {
        var label = new Label(header);
        var copy = new Button("Copy");
        copy.setMinWidth(70);
        copy.setOnAction(e -> {
            var clipboard = Clipboard.getSystemClipboard();
            var content = new ClipboardContent();
            content.putHtml((String) webView.getEngine().executeScript("document.documentElement.outerHTML"));
            clipboard.setContent(content);
        });
        statusCircle.setRadius(10);
        statusCircle.setFill(Color.WHITE);
        statusCircle.setId(STATUS_CIRCLE_ID);
        getChildren().addAll(label, new Separator(), webView, new Separator(), copy, statusCircle);
        setHgrow(webView, Priority.ALWAYS);
    }

    public void setContent(String content) {
        webView.getEngine().loadContent(content);
    }

    public ObjectProperty<Paint> statusCircleFillProperty() {
        return statusCircle.fillProperty();
    }
}
