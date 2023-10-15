package javafx.shortcut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuItemAcceleratorApp extends Application {
    @Override
    public void start(Stage stage) {
        var menuItem = new MenuItem("Print to console (shortcut is Ctrl-1)");
        menuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN));
        menuItem.setOnAction(e -> System.out.println("Shortcut triggered!"));
        var menu = new Menu("Settings");
        menu.getItems().add(menuItem);
        var menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        var vBox = new VBox(menuBar);
        var scene = new Scene(vBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}