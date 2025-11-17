package testfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClickApplication extends Application {

    @Override
    public void start(Stage stage) {
        var sceneRoot = new ClickPane();
        var scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    public static class ClickPane extends StackPane {
        public ClickPane() {
            var button = new Button("click me!");
            button.setOnAction(_ -> button.setText("clicked!"));
            getChildren().add(button);
        }
    }
}
