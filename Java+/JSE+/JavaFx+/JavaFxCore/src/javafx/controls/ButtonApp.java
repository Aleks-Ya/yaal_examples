package javafx.controls;

import javafx.application.Application;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonApp extends Application {

    @Override
    public void start(Stage stage) {
        var button1 = onActionButton();
        var button2 = setOnActionPropertyButton();
        var button3 = bindOnActionPropertyButton();
        var button4 = disabledButton();
        var vBox = new VBox(button1, new Separator(), button2, new Separator(), button3, new Separator(), button4);
        var scene = new Scene(vBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Button onActionButton() {
        var button = new Button("Print to console (onAction)");
        button.setOnAction(_ -> System.out.println("Button was clicked (onAction)"));
        return button;
    }

    private static Button setOnActionPropertyButton() {
        var button = new Button("Print to console (set onActionProperty)");
        button.onActionProperty().set(_ -> System.out.println("Button was clicked (set onActionProperty)"));
        return button;
    }

    private static Button bindOnActionPropertyButton() {
        var button = new Button("Print to console (bind onActionProperty)");
        button.onActionProperty().bind(new ObservableValueBase<>() {
            @Override
            public EventHandler<ActionEvent> getValue() {
                return _ -> System.out.println("Button was clicked (bind onActionProperty)");
            }
        });
        return button;
    }

    private static Button disabledButton() {
        var button = new Button("Disabled");
        button.setDisable(true);
        button.setOnAction(_ -> {
            throw new AssertionError("Button is disabled");
        });
        return button;
    }
}