package gptui.ui.view;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

class QuestionCorrectnessPane extends HBox {
    private final WebView webView = new WebView();

    public QuestionCorrectnessPane() {
        var label = new Label("Question\ncorrectness:");
        var sep = new Separator();
        getChildren().addAll(label, sep, webView);
        webView.setId("QuestionCorrectnessWebView");
        webView.setPrefHeight(200);
        webView.addEventFilter(KEY_PRESSED, new PropagateCtrlVToParent());
        HBox.setHgrow(webView, Priority.ALWAYS);
    }

    public void setContent(String content) {
        webView.getEngine().loadContent(content);
    }
}