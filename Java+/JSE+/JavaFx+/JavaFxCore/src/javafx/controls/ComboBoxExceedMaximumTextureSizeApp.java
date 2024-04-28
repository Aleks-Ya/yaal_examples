package javafx.controls;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.stream.IntStream;

/**
 * Reproduce error:
 * <code>"RuntimeException: Requested texture dimensions (27604x267)
 * require dimensions (0x267) that exceed maximum texture size (16384)"</code>
 * when click on a ComboBox having too many long items.
 */
public class ComboBoxExceedMaximumTextureSizeApp extends Application {
    @Override
    public void start(Stage stage) {
        var comboBox = new ComboBox<>();
        var item = "abc ".repeat(1000);
        var items = IntStream.range(0, 10000).mapToObj(i -> i + item).toList();
        comboBox.setItems(FXCollections.observableArrayList(items));
        var scene = new Scene(new VBox(comboBox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}