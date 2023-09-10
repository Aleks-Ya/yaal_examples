package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class HTMLEditorApp extends Application {
    @Override
    public void start(Stage stage) {
        var editor = new HTMLEditor();
        var scene = new Scene(new StackPane(editor), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}