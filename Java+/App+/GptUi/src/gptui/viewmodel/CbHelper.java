package gptui.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CbHelper {
    public static void updateCbSilently(Runnable run, ObjectProperty<EventHandler<ActionEvent>> onAction) {
        var oldOnAction = onAction.getValue();
        onAction.setValue(null);
        run.run();
        onAction.setValue(oldOnAction);
    }
}
