package gptui.ui.controller;

import gptui.storage.Interaction;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;

import java.util.Objects;
import java.util.Optional;

public class ThemeController extends BaseController {
    @FXML
    private ComboBox<String> themeComboBox;

    @FXML
    void themeComboBoxKeyReleased(KeyEvent ignoredEvent) {
        model.setCurrentTheme(themeComboBox.getEditor().getText());
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        setItems(model);
        var currentModelValue = model.getEditedTheme();
        var currentComboBoxValue = themeComboBox.getValue();
        if (!Objects.equals(currentModelValue, currentComboBoxValue)) {
            themeComboBox.setValue(currentModelValue);
        }
    }

    @Override
    public void interactionChosenFromHistory(Model model, EventSource source) {
        setItems(model);
        var currentComboBoxValue = themeComboBox.getValue();
        Optional.ofNullable(model.getCurrentInteraction())
                .map(Interaction::theme)
                .filter(theme -> !theme.equals(currentComboBoxValue))
                .ifPresent(theme -> themeComboBox.setValue(theme));
    }

    @FXML
    void themeComboBoxAction(ActionEvent ignoredEvent) {
        var currentComboBoxValue = themeComboBox.getValue();
        var currentModelValue = model.getEditedTheme();
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            model.setCurrentTheme(currentComboBoxValue);
            model.fireModelChanged(this);
        }
    }

    private void setItems(Model model) {
        var currentModelItems = FXCollections.observableArrayList(model.getThemeList());
        var currentComboBoxItems = themeComboBox.getItems();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            themeComboBox.setItems(currentModelItems);
        }
    }
}
