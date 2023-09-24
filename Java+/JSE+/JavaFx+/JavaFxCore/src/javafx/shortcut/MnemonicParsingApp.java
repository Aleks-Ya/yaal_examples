package javafx.shortcut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MnemonicParsingApp extends Application {
    @Override
    public void start(Stage stage) {
        var button1 = onActionButton();
        var vBox = new VBox(button1);
        var scene = new Scene(vBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Button onActionButton() {
        var button = new Button("_Print to console (shortcut is Alt-P)");
        button.setMnemonicParsing(true);
        button.setOnAction(evt -> System.out.println("Button was clicked"));
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}