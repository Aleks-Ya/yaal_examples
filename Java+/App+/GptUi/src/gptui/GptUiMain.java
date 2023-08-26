package gptui;

import gptui.ui.GptView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GptUiMain extends Application {
    @Override
    public void start(Stage stage) {
        var view = new GptView();
        var scene = new Scene(view, 640, 900);
        stage.setScene(scene);
        stage.setTitle("GPT-4 Question Client");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}