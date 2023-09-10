package javafx.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var hBox1 = simpleHBox();
        var hBox2 = hGrowHBox();
        var scene = new Scene(new VBox(hBox1, hBox2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static HBox simpleHBox() {
        var button1 = new Button("Button 1");
        var button2 = new Button("Button 2");
        var button3 = new Button("Button 3");
        return new HBox(button1, button2, button3);
    }

    private static HBox hGrowHBox() {
        var button1 = new TextField("TextField 1");
        var button2 = new TextField("TextField 2");
        var button3 = new TextField("TextField 3");
        var hBox = new HBox(button1, button2, button3);
        HBox.setHgrow(button2, Priority.ALWAYS);
        return hBox;
    }

    public static void main(String[] args) {
        launch();
    }
}