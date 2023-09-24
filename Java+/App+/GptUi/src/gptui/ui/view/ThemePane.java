package gptui.ui.view;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

class ThemePane extends HBox {
    private final ComboBox<String> comboBox = new ComboBox<>();

    public ThemePane() {
        var label = new Label("Theme:");
        label.setId("ThemeLabel");
        var sep = new Separator();
        comboBox.setId("ThemeComboBox");
        comboBox.setEditable(true);
        getChildren().addAll(label, sep, comboBox);
    }

    public ObjectProperty<String> getValueProperty() {
        return comboBox.valueProperty();
    }

    public ObjectProperty<ObservableList<String>> getItemsProperty() {
        return comboBox.itemsProperty();
    }

    public ObservableList<String> getItems() {
        return comboBox.getItems();
    }
}
