package controlsfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class SearchableComboBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var list = FXCollections.observableArrayList("Medium", "High", "Low");
        var searchableComboBox = new SearchableComboBox<>(list);
        var scene = new Scene(new VBox(searchableComboBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}