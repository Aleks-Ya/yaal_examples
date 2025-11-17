package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SwitchRootNodesInSceneApp extends Application {

    @Override
    public void start(Stage stage) {
        var label1 = new Label("It's the Root Node 1");
        var switchToRootNode2Button = new Button("Switch to Root Node 2");
        var pane1 = new HBox(label1, new Separator(), switchToRootNode2Button);

        var label2 = new Label("It's the Root Node 2");
        var switchToRootNode1Button = new Button("Switch to Root Node 1");
        var pane2 = new HBox(label2, new Separator(), switchToRootNode1Button);

        var scene = new Scene(pane1, 600, 500);

        switchToRootNode2Button.setOnAction(_ -> scene.setRoot(pane2));
        switchToRootNode1Button.setOnAction(_ -> scene.setRoot(pane1));

        stage.setScene(scene);
        stage.show();
    }

}