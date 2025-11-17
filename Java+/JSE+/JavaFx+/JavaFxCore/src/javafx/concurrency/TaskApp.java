package javafx.concurrency;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TaskApp extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Not started");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();

        var task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000);
                Platform.runLater(() -> label.setText("Working..."));
                Thread.sleep(5000);
                Platform.runLater(() -> label.setText("Finished"));
                return null;
            }
        };
        new Thread(task).start();
    }

}