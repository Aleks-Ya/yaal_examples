package javafx.screen.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageResizableApp extends Application {

    @Override
    public void start(Stage stage) {
        var isResizableCheckBox = new CheckBox("Resizable");
        isResizableCheckBox.setSelected(stage.isResizable());
        isResizableCheckBox.selectedProperty()
                .addListener((_, _, newValue) -> stage.setResizable(newValue));

        var minHeight = new Spinner<Integer>(0, 2000, 0, 10);
        minHeight.setEditable(true);
        var maxHeight = new Spinner<Integer>(0, 2000, 0, 10);
        maxHeight.setEditable(true);
        var minWidth = new Spinner<Integer>(0, 2000, 0, 10);
        minWidth.setEditable(true);
        var maxWidth = new Spinner<Integer>(0, 2000, 0, 10);
        maxWidth.setEditable(true);

        var xLabel = new Label();
        var yLabel = new Label();
        var widthLabel = new Label();
        var heightLabel = new Label();

        stage.xProperty().addListener(
                (_, _, _) -> xLabel.setText("X: " + stage.getX()));
        stage.yProperty().addListener(
                (_, _, _) -> yLabel.setText("Y: " + stage.getY()));
        stage.widthProperty().addListener(
                (_, _, _) -> widthLabel.setText("Width: " + stage.getWidth()));
        stage.heightProperty().addListener(
                (_, _, _) -> heightLabel.setText("Height: " + stage.getHeight()));

        var box = new VBox(
                xLabel,
                yLabel, new Separator(),
                widthLabel,
                heightLabel, new Separator(),
                isResizableCheckBox, new Separator(),
                minHeightHBox(stage, minHeight),
                maxHeightHBox(stage, maxHeight), new Separator(),
                minWidthHBox(stage, minWidth),
                maxWidthHBox(stage, maxWidth));
        var scene = new Scene(box, 640, 480);
        stage.setScene(scene);
        stage.show();

        minHeight.getValueFactory().setValue(Double.valueOf(stage.getMinHeight()).intValue());
        maxHeight.getValueFactory().setValue(Double.valueOf(stage.getMaxHeight()).intValue());
        minWidth.getValueFactory().setValue(Double.valueOf(stage.getMinWidth()).intValue());
        maxWidth.getValueFactory().setValue(Double.valueOf(stage.getMaxWidth()).intValue());
    }

    private static HBox minHeightHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Min Height: ");
        spinner.valueProperty().addListener(
                (_, _, newValue) -> stage.setMinHeight(newValue));
        return new HBox(label, spinner);
    }

    private static HBox maxHeightHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Max Height: ");
        spinner.valueProperty().addListener(
                (_, _, newValue) -> stage.setMaxHeight(newValue));
        return new HBox(label, spinner);
    }

    private static HBox minWidthHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Min Width: ");
        spinner.valueProperty().addListener(
                (_, _, newValue) -> stage.setMinWidth(newValue));
        return new HBox(label, spinner);
    }

    private static HBox maxWidthHBox(Stage stage, Spinner<Integer> spinner) {
        var label = new Label("Max Width: ");
        spinner.valueProperty().addListener(
                (_, _, newValue) -> stage.setMaxWidth(newValue));
        return new HBox(label, spinner);
    }

}