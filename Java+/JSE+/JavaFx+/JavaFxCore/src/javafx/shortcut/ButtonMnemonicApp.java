package javafx.shortcut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonMnemonicApp extends Application {
    @Override
    public void start(Stage stage) {
        var button1 = onActionButton();
        var kc = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.ALT_DOWN);
        var mnemonic = new Mnemonic(button1, kc);
        var vBox = new VBox(button1);
        var scene = new Scene(vBox, 640, 480);
        scene.addMnemonic(mnemonic);
        stage.setScene(scene);
        stage.show();
    }

    private static Button onActionButton() {
        var button = new Button("Print to console (shortcut is Alt-1)");
        button.setOnAction(evt -> System.out.println("Button was clicked"));
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}