package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageSizeApp extends Application {
    @Override
    public void start(Stage stage) {
        var stageX = new Spinner<Integer>(0, 2000, 0, 10);
        var stageY = new Spinner<Integer>(0, 2000, 0, 10);
        var stageWidth = new Spinner<Integer>(0, 2000, 0, 10);
        var stageHeight = new Spinner<Integer>(0, 2000, 0, 10);
        var box = new VBox(
                stageXHBox(stage, stageX), new Separator(),
                stageYHBox(stage, stageY), new Separator(),
                stageWidthHBox(stage, stageWidth), new Separator(),
                stageHeightHBox(stage, stageHeight));
        var scene = new Scene(box, 640, 480);
        stage.setScene(scene);
        stage.show();
        stageX.getValueFactory().setValue(Double.valueOf(stage.getX()).intValue());
        stageY.getValueFactory().setValue(Double.valueOf(stage.getY()).intValue());
        stageWidth.getValueFactory().setValue(Double.valueOf(stage.getWidth()).intValue());
        stageHeight.getValueFactory().setValue(Double.valueOf(stage.getHeight()).intValue());
    }

    private static HBox stageXHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Stage X: ");
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> stage.setX(newValue));
        return new HBox(label, spinner);
    }

    private static HBox stageYHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Stage Y: ");
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> stage.setY(newValue));
        return new HBox(label, spinner);
    }

    private static HBox stageWidthHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Stage Width: ");
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> stage.setWidth(newValue));
        return new HBox(label, spinner);
    }

    private static HBox stageHeightHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Stage Height: ");
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> stage.setHeight(newValue));
        return new HBox(label, spinner);
    }

    public static void main(String[] args) {
        launch();
    }
}