package gptui.ui.controller;

import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionStorage;
import gptui.ui.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
    @FXML
    private CheckBox filterHistoryCheckBox;
    @Inject
    private InteractionStorage storage;

    @FXML
    void themeComboBoxKeyReleased(KeyEvent ignoredEvent) {
        log.trace("themeComboBoxKeyReleased");
        model.setCurrentTheme(themeComboBox.getEditor().getText());
    }

    @FXML
    void themeComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("themeComboBoxAction");
        var currentComboBoxValue = themeComboBox.getValue();
        var currentModelValue = model.getCurrentTheme();
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            model.setCurrentTheme(currentComboBoxValue);
            model.fire().themeWasChosen(this);
        }
    }

    @FXML
    void themeFilterHistoryCheckBoxClicked(ActionEvent ignore) {
        log.trace("themeFilterHistoryCheckBoxClicked");
        if (filterHistoryCheckBox.isSelected() != model.isThemeFilterHistory()) {
            model.setIsThemeFilterHistory(filterHistoryCheckBox.isSelected());
            log.debug("ThemeFilterHistoryCheckBox is set to {}", model.isThemeFilterHistory());
            model.fire().isThemeFilterHistoryChanged(this);
        }
    }

    @Override
    public void stageWasShowed(Model model) {
        log.trace("stageWasShowed");
        setLabel(storage.getThemes().size());
    }

    @Override
    public void interactionChosenFromHistory(Model model) {
        log.trace("interactionChosenFromHistory");
        updateComboBoxCurrentValue(model.getCurrentInteractionId());
    }

    @Override
    public void interactionsUpdated(Model model) {
        log.trace("interactionsUpdated");
        updateComboBoxItems();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    private void updateComboBoxCurrentValue(InteractionId currentInteractionId) {
        Optional.ofNullable(currentInteractionId)
                .map(storage::readInteraction)
                .map(Optional::orElseThrow)
                .map(Interaction::theme)
                .ifPresent(theme -> themeComboBox.setValue(theme));
    }

    private void updateComboBoxItems() {
        var currentModelItems = FXCollections.observableArrayList(storage.getThemes());
        var currentComboBoxItems = themeComboBox.getItems();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            themeComboBox.setItems(currentModelItems);
            setLabel(storage.getThemes().size());
        }
    }

    private void setLabel(int size) {
        themeLabel.setText(String.format("Theme (%d):", size));
    }
}
