package gptui.viewmodel.theme;

import gptui.model.storage.Theme;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ThemeVmProperties {
    public final ObjectProperty<Theme> themeCbValue = new SimpleObjectProperty<>();
    public final ListProperty<Theme> themeCbItems = new SimpleListProperty<>();
    public final StringProperty themeCbEditor = new SimpleStringProperty();
    public final ObjectProperty<EventHandler<ActionEvent>> themeCbOnAction = new SimpleObjectProperty<>();
    public final ObjectProperty<Callback<ListView<Theme>, ListCell<Theme>>> themeCbCellFactory = new SimpleObjectProperty<>();
    public final BooleanProperty filterHistoryCheckBoxSelected = new SimpleBooleanProperty();
    public final StringProperty themeLabelText = new SimpleStringProperty();
}
