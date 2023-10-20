package gptui.ui.controller;

import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class ThemeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ThemeController.class);
    @FXML
    private Label themeLabel;
    @FXML
    private ComboBox<String> themeComboBox;
    @Inject
    private GptStorage storage;
    @Inject
    private ThemeHelper themeHelper;

    @FXML
    void themeComboBoxKeyReleased(KeyEvent ignoredEvent) {
        model.setCurrentTheme(themeComboBox.getEditor().getText());
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        log.trace("modelChanged");
        setItems(model);
        var currentModelValue = model.getEditedTheme();
        var currentComboBoxValue = themeComboBox.getValue();
        if (!Objects.equals(currentModelValue, currentComboBoxValue)) {
            themeComboBox.setValue(currentModelValue);
        }
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        var history = model.getHistory().stream().map(storage::readInteraction).map(Optional::orElseThrow).toList();
        var themeList = themeHelper.interactionsToThemeList(history);
        setLabel(themeList.size());
    }

    @Override
    public void interactionChosenFromHistory(Model model, EventSource source) {
        log.trace("interactionChosenFromHistory");
        setItems(model);
        var currentComboBoxValue = themeComboBox.getValue();
        Optional.ofNullable(model.getCurrentInteractionId())
                .map(storage::readInteraction)
                .map(Optional::orElseThrow)
                .map(Interaction::theme)
                .filter(theme -> !theme.equals(currentComboBoxValue))
                .ifPresent(theme -> themeComboBox.setValue(theme));
    }

    @FXML
    void themeComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("themeComboBoxAction");
        var currentComboBoxValue = themeComboBox.getValue();
        var currentModelValue = model.getEditedTheme();
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            model.setCurrentTheme(currentComboBoxValue);
            model.fireModelChanged(this);
        }
    }

    private void setItems(Model model) {
        var history = model.getHistory().stream().map(storage::readInteraction).map(Optional::orElseThrow).toList();
        var themeList = themeHelper.interactionsToThemeList(history);
        var currentModelItems = FXCollections.observableArrayList(themeList);
        var currentComboBoxItems = themeComboBox.getItems();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            themeComboBox.setItems(currentModelItems);
            setLabel(themeList.size());
        }
    }

    private void setLabel(int size) {
        themeLabel.setText(String.format("Theme (%d):", size));
    }
}
