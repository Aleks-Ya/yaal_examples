package javafx.shortcut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneAcceleratorApp extends Application {

    @Override
    public void start(Stage stage) {
        var kc = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
        var scene = new Scene(new StackPane(new Label("Shortcut is Ctrl-1")), 640, 480);
        Runnable task = () -> System.out.println("Shortcut is triggered!");
        scene.getAccelerators().put(kc, task);
        stage.setScene(scene);
        stage.show();
    }

}