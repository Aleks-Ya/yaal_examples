package gptui.ui.view;

import gptui.storage.Interaction;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

class InteractionHistoryPane extends HBox {
    private final ComboBox<Interaction> comboBox = new ComboBox<>();

    public InteractionHistoryPane() {
        var label = new Label("Question history:");
        label.setId("#InteractionHistoryLabel");
        var sep = new Separator();
        comboBox.setId("InteractionHistoryComboBox");
        getChildren().addAll(label, sep, comboBox);
    }

    public ObjectProperty<Interaction> getValueProperty() {
        return comboBox.valueProperty();
    }

    public ObjectProperty<ObservableList<Interaction>> getItemsProperty() {
        return comboBox.itemsProperty();
    }
}
