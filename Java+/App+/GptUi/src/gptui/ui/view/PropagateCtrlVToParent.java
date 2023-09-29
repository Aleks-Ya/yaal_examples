package gptui.ui.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

class PropagateCtrlVToParent implements EventHandler<KeyEvent> {
    private final KeyCombination keyComb = new KeyCodeCombination(V, CONTROL_DOWN);

    public void handle(KeyEvent e) {
        if (keyComb.match(e)) {
            e.consume();
            ((Node) e.getTarget()).getParent().fireEvent(e);
        }
    }
}
