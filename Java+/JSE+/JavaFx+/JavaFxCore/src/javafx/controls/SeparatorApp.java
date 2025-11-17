package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SeparatorApp extends Application {

    @Override
    public void start(Stage stage) {
        var button1 = new Button("Button 1");
        var sep1 = new Separator();
        var button2 = new Button("Button 2");
        var sep2 = new Separator();
        var button3 = new Button("Button 3");
        var scene = new Scene(new HBox(button1, sep1, button2, sep2, button3), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}