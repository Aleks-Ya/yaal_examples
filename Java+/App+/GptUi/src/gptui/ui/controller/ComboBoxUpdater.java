package gptui.ui.controller;

import javafx.scene.control.ComboBox;

import java.util.function.Consumer;

class ComboBoxUpdater {
    public static <T> void updateSilently(ComboBox<T> comboBox, Consumer<ComboBox<T>> update) {
        var oldOnAction = comboBox.getOnAction();
        comboBox.setOnAction(null);
        update.accept(comboBox);
        comboBox.setOnAction(oldOnAction);
    }
}
