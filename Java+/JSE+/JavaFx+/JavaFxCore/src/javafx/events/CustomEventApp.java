package javafx.events;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomEventApp extends Application {

    @Override
    public void start(Stage stage) {
        var button = new Button("Click me");
        button.setOnAction(_ -> stage.fireEvent(new CustomEvent()));
        var vBox = new VBox(button);
        var scene = new Scene(vBox, 640, 480);
        stage.addEventHandler(CustomEvent.ANY, _ -> System.out.println("Custom event was handled"));
        stage.setScene(scene);
        stage.show();
    }

    public static class CustomEvent extends Event {
        public static final EventType<CustomEvent> ANY = new EventType<>(Event.ANY, "CUSTOM");

        public CustomEvent() {
            super(ANY);
        }
    }
}