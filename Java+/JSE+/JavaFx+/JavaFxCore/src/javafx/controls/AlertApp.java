package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertApp extends Application {
    @Override
    public void start(Stage stage) {
        var okButton = okAlert();
        var yesNoButton = yesNoAlert();
        var scene = new Scene(new VBox(okButton, yesNoButton), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Button okAlert() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The title");
        alert.setHeaderText("The header");
        alert.setContentText("The content text");

        var button = new Button("Show an OK Alert");
        button.setOnAction(evt -> alert.showAndWait());
        return button;
    }

    private static Button yesNoAlert() {
        var buttonTypeYes = new ButtonType("Yes");
        var buttonTypeNo = new ButtonType("No");

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The title");
        alert.setHeaderText("The header");
        alert.setContentText("The content text");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        var button = new Button("Show an YES/NO Alert");
        button.setOnAction(evt -> {
            var result = alert.showAndWait();
            if (result.orElseThrow() == buttonTypeYes) {
                System.out.println("User answered YES");
            } else {
                System.out.println("User answered NO");
            }
        });
        return button;
    }

    public static void main(String[] args) {
        launch();
    }
}