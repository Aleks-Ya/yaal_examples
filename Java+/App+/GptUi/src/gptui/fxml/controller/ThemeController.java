package gptui.fxml.controller;

import gptui.fxml.Model;
import gptui.storage.Interaction;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Objects;
import java.util.Optional;

class ThemeController extends BaseController {
    @FXML
    private ComboBox<String> themeComboBox;

    @Override
    public void modelChanged(Model model) {
        setItems(model);
        var currentModelValue = model.getCurrentTheme();
        var currentComboBoxValue = themeComboBox.getValue();
        if (!Objects.equals(currentModelValue, currentComboBoxValue)) {
            themeComboBox.setValue(currentModelValue);
        }
    }

    @Override
    public void interactionChosenFromHistory(Model model) {
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
        var currentModelValue = model.getCurrentTheme();
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            model.setCurrentTheme(currentComboBoxValue);
            model.fireModelChanged();
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