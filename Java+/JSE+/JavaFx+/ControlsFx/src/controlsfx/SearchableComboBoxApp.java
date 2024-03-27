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
        var list = FXCollections.observableArrayList("Medium", "High", "Highest", "Low");
        var searchableComboBox = new SearchableComboBox<>(list);
        searchableComboBox.setOnAction(event -> System.out.printf("On Action: %s\n", event));
        searchableComboBox.setOnKeyPressed(event -> System.out.printf("On key pressed: %s\n", event));
        searchableComboBox.setOnKeyReleased(event -> System.out.printf("On key released: %s\n", event));
        searchableComboBox.setOnKeyTyped(event -> System.out.printf("On Key typed: %s\n", event));
        var scene = new Scene(new VBox(searchableComboBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}