package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SwitchScenesApp extends Application {
    @Override
    public void start(Stage stage) {
        var label1 = new Label("It's the Scene 1");
        var switchToScene2Button = new Button("Switch to Scene 2");
        var pane1 = new HBox(label1, new Separator(), switchToScene2Button);
        var scene1 = new Scene(pane1, 300, 200);

        var label2 = new Label("It's the Scene 2");
        var switchToScene1Button = new Button("Switch to Scene 1");
        var pane2 = new HBox(label2, new Separator(), switchToScene1Button);
        var scene2 = new Scene(pane2, 600, 500);

        switchToScene2Button.setOnAction(event -> stage.setScene(scene2));
        switchToScene1Button.setOnAction(event -> stage.setScene(scene1));

        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}