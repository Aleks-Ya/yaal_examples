package controlsfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class SearchableComboBoxApp extends Application {
    @Override
    public void start(Stage stage) {
        var list = FXCollections.observableArrayList("Medium", "High", "Highest", "Low");
        var searchableComboBox = new SearchableComboBox<>(list);
        var counter = new AtomicInteger();

        searchableComboBox.setOnAction(event -> out.printf("%d OnAction: %s\n", counter.incrementAndGet(), event));

        searchableComboBox.setOnKeyPressed(event -> out.printf("%d OnKeyPressed: %s\n", counter.incrementAndGet(), event));
        searchableComboBox.setOnKeyReleased(event -> out.printf("%d OnKeyReleased: %s\n", counter.incrementAndGet(), event));
        searchableComboBox.setOnKeyTyped(event -> out.printf("%d OnKeyTyped: %s\n", counter.incrementAndGet(), event));

        searchableComboBox.setOnMouseClicked(event -> out.printf("%d OnMouseClicked: %s\n", counter.incrementAndGet(), event));
        searchableComboBox.setOnMousePressed(event -> out.printf("%d OnMousePressed: %s\n", counter.incrementAndGet(), event));
        searchableComboBox.setOnMouseReleased(event -> out.printf("%d OnMouseReleased: %s\n", counter.incrementAndGet(), event));
        searchableComboBox.getItems().addListener((ListChangeListener<? super String>) c ->
                out.printf("%d Items Listener: %s\n", counter.incrementAndGet(), c));

        searchableComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                out.printf("%d ChangeListener: %s->%s\n", counter.incrementAndGet(), oldValue, newValue));
        searchableComboBox.getSelectionModel().selectedItemProperty().addListener(observable ->
                out.printf("%d InvalidationListener: %s\n", counter.incrementAndGet(), observable));

        searchableComboBox.showingProperty().addListener((observable, oldValue, newValue) ->
                out.printf("%d ShowingListener: %s->%s\n", counter.incrementAndGet(), oldValue, newValue));
        searchableComboBox.onShowingProperty().addListener((observable, oldValue, newValue) ->
                out.printf("%d OnShowingListener: %s->%s\n", counter.incrementAndGet(), oldValue, newValue));
        searchableComboBox.onShownProperty().addListener((observable, oldValue, newValue) ->
                out.printf("%d OnShownListener: %s->%s\n", counter.incrementAndGet(), oldValue, newValue));

        var scene = new Scene(new VBox(searchableComboBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}