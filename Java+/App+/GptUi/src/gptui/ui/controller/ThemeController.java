package gptui.ui.controller;

import gptui.storage.Interaction;
import gptui.storage.InteractionStorage;
import gptui.ui.EventSource;
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

    @Override
    public void modelChanged(Model model) {
        log.trace("modelChanged");
        setItems();
        var currentModelValue = model.getCurrentTheme();
        var currentComboBoxValue = themeComboBox.getValue();
        if (!Objects.equals(currentModelValue, currentComboBoxValue)) {
            themeComboBox.setValue(currentModelValue);
        }
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        setLabel(storage.getThemes().size());
    }

    @Override
    public void interactionChosenFromHistory(Model model) {
        log.trace("interactionChosenFromHistory");
        setItems();
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
        var currentModelValue = model.getCurrentTheme();
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            model.setCurrentTheme(currentComboBoxValue);
            model.fireModelChanged(this);
        }
    }

    @FXML
    void themeFilterHistoryCheckBoxClicked(ActionEvent ignore) {
        log.trace("FilterHistoryCheckBox was clicked");
        if (filterHistoryCheckBox.isSelected() != model.isThemeFilterHistory()) {
            model.setIsThemeFilterHistory(filterHistoryCheckBox.isSelected());
            log.debug("ThemeFilterHistoryCheckBox is set to {}", model.isThemeFilterHistory());
            model.fireModelChanged(this);
        }
    }

    private void setItems() {
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

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
