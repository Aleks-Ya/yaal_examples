package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SliderApp extends Application {
    @Override
    public void start(Stage stage) {
        var wideSlider = wideSlider();
        var shortSlider = shortSlider();
        var tickSlider = tickSlider();
        var scene = new Scene(new VBox(wideSlider, shortSlider, tickSlider), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static VBox wideSlider() {
        var initValue = 30;
        var text = new Text(String.valueOf(initValue));
        var slider = new Slider(0, 100, initValue);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> text.setText(newValue.toString()));
        return new VBox(text, slider);
    }

    private static VBox shortSlider() {
        var initValue = 30;
        var text = new Text(String.valueOf(initValue));
        var slider = new Slider(0, 100, initValue);
        slider.setMaxWidth(50);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> text.setText(newValue.toString()));
        return new VBox(text, slider);
    }

    /**
     * Can choose values multiple to 10.
     */
    private static VBox tickSlider() {
        var initValue = 30;
        var text = new Text(String.valueOf(initValue));
        var slider = new Slider(0, 100, initValue);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10);
        slider.setSnapToTicks(true);
        slider.setMinorTickCount(0);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> text.setText(newValue.toString()));
        return new VBox(text, slider);
    }

    public static void main(String[] args) {
        launch();
    }
}