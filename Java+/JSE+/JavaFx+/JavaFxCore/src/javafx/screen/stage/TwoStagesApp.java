package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TwoStagesApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        var firstStageLabel = new Label("This is the first stage");
        var firstScene = new Scene(firstStageLabel, 400, 300);
        primaryStage.setTitle("First Stage");
        primaryStage.setScene(firstScene);
        primaryStage.setX(100);
        primaryStage.setY(100);
        primaryStage.show();

        var secondStage = new Stage();
        var secondStageLabel = new Label("This is the second stage");
        var secondScene = new Scene(secondStageLabel, 300, 100);
        secondStage.setTitle("Second Stage");
        secondStage.setScene(secondScene);
        secondStage.setX(500);
        secondStage.setY(600);
        secondStage.show();
    }

}