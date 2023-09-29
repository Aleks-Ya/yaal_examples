package javafx.shortcut;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

/**
 * Suppress Ctrl-V on a WebView.
 */
public class CtrlVSceneAcceleratorApp extends Application {
    @Override
    public void start(Stage stage) {
        var button = new Button("Focus on me and press Ctrl-V");
        var webView = new WebView();
        webView.getEngine().loadContent("Focus on me and press Ctrl-V");
        webView.addEventFilter(KEY_PRESSED, new PropagateCtrlVToParent());

        var kc = new KeyCodeCombination(V, CONTROL_DOWN);
        var vBox = new VBox(button, webView);
        vBox.addEventHandler(KEY_PRESSED, new EventHandler<>() {
            private final KeyCombination keyComb = new KeyCodeCombination(V, CONTROL_DOWN);

            public void handle(KeyEvent e) {
                if (keyComb.match(e)) {
                    e.consume();
                    System.out.println("Handled by VBox");
                }
            }
        });
        var scene = new Scene(vBox, 640, 480);
        Runnable task = () -> System.out.println("Global shortcut is triggered!");
        scene.getAccelerators().put(kc, task);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    static class PropagateCtrlVToParent implements EventHandler<KeyEvent> {
        private final KeyCombination keyComb = new KeyCodeCombination(V, CONTROL_DOWN);

        public void handle(KeyEvent e) {
            if (keyComb.match(e)) {
                e.consume();
                System.out.println("Re-fired by WebView to VBox");
                ((Node) e.getTarget()).getParent().fireEvent(e);
            }
        }
    }
}