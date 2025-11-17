package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SpinnerApp extends Application {

    @Override
    public void start(Stage stage) {
        var spinner1 = spinner();
        var spinner2 = spinnerEditable();
        var spinner3 = spinnerSuffix();
        var spinner4 = spinnerListener();
        var scene = new Scene(new VBox(spinner1, spinner2, spinner3, spinner4), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static HBox spinner() {
        var label = new Label("Not editable Spinner: ");
        var spinner = new Spinner<Integer>(0, 100, 50, 5);
        return new HBox(label, spinner);
    }

    private static HBox spinnerEditable() {
        var label = new Label("Editable Spinner: ");
        var spinner = new Spinner<Integer>(0, 100, 50, 5);
        spinner.setEditable(true);
        return new HBox(label, spinner);
    }

    private static HBox spinnerSuffix() {
        var label = new Label("Spinner with suffix: ");
        var spinner = new Spinner<Integer>(0, 100, 50, 5);
        spinner.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer number) {
                return number + "°";
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string.replace("°", ""));
            }
        });
        return new HBox(label, spinner);
    }

    private static HBox spinnerListener() {
        var label = new Label("Spinner with listener (see console): ");
        var spinner = new Spinner<Integer>(0, 100, 50, 5);
        spinner.valueProperty().addListener((_, oldValue, newValue) ->
                System.out.printf("Spinner: oldValue=%d, newValue=%d\n", oldValue, newValue));
        return new HBox(label, spinner);
    }

}