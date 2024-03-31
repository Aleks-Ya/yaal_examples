package gptui.viewmodel.history;

import gptui.viewmodel.InteractionItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SingleSelectionModel;

public class HistoryVmProperties {
    public final StringProperty historyLabelText = new SimpleStringProperty();
    public final ObjectProperty<SingleSelectionModel<InteractionItem>> historyCbSelectionModel = new SimpleObjectProperty<>();
    public final BooleanProperty historyDeleteButtonDisable = new SimpleBooleanProperty();
    public final ObjectProperty<ObservableList<InteractionItem>> historyCbItems = new SimpleObjectProperty<>();
    public final ObjectProperty<EventHandler<ActionEvent>> historyCbOnAction = new SimpleObjectProperty<>();
}
