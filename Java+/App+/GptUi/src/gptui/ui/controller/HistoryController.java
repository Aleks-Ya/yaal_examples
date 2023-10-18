package gptui.ui.controller;

import gptui.format.ThemeHelper;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;

import static gptui.ui.controller.ComboBoxUpdater.updateSilently;
import static java.lang.String.format;

public class HistoryController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private GptStorage storage;
    @FXML
    private Label historyLabel;
    @FXML
    private ComboBox<Interaction> historyComboBox;
    @FXML
    private Button historyDeleteButton;
    @Inject
    private ThemeHelper themeHelper;

    @Override
    public void modelChanged(Model model, EventSource source) {
        log.trace("modelChanged");
        var modelItems = FXCollections.observableArrayList(model.getHistory());
        var comboBoxItems = historyComboBox.getItems();
        if (!Objects.equals(modelItems, comboBoxItems)) {
            log.debug("Set items");
            updateSilently(historyComboBox, comboBox -> comboBox.setItems(modelItems));
            setLabel(model);
        }
        var modelCurrentValue = model.getCurrentInteraction();
        var comboBoxCurrentValue = historyComboBox.getSelectionModel().getSelectedItem();
        if (!Objects.equals(modelCurrentValue, comboBoxCurrentValue)) {
            if (modelCurrentValue != null) {
                log.debug("Select interaction: {}", modelCurrentValue);
                updateSilently(historyComboBox, comboBox -> comboBox.getSelectionModel().select(modelCurrentValue));
            } else {
                log.debug("Clear selection");
                updateSilently(historyComboBox, comboBox -> comboBox.getSelectionModel().clearSelection());
            }
        } else {
            log.debug("Selection is unchanged: {}", modelCurrentValue);
        }
        historyDeleteButton.setDisable(model.getCurrentInteraction() == null);
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        setLabel(model);
    }

    @FXML
    void historyComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("historyComboBoxAction");
        var modelCurrentInteraction = model.getCurrentInteraction();
        var comboBoxCurrentInteraction = historyComboBox.getSelectionModel().getSelectedItem();
        if (comboBoxCurrentInteraction != null && !Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
            log.debug("setCurrentInteraction from historyComboBox: {}", comboBoxCurrentInteraction);
            model.setCurrentInteraction(comboBoxCurrentInteraction);
            model.fireInteractionChosenFromHistory(this);
        }
    }

    @FXML
    void clickHistoryDeleteButton(ActionEvent ignoredEvent) {
        log.trace("clickHistoryDeleteButton");
        var oldCurrentInteractionIndex = historyComboBox.getSelectionModel().getSelectedIndex();
        storage.deleteInteraction(model.getCurrentInteraction().id());


        var newHistory = storage.readAllInteractions();
        model.setHistory(newHistory);
        model.setThemeList(themeHelper.interactionsToThemeList(newHistory));

        if (!newHistory.isEmpty()) {
            var newCurrentInteractionIndex = oldCurrentInteractionIndex - 1;
            Interaction newCurrentInteraction;
            if (newCurrentInteractionIndex >= 0 && newCurrentInteractionIndex < newHistory.size()) {
                newCurrentInteraction = newHistory.get(newCurrentInteractionIndex);
            } else {
                newCurrentInteraction = newHistory.get(newHistory.size() - 1);
            }
            model.setCurrentInteraction(newCurrentInteraction);
        } else {
            model.setCurrentInteraction(null);
        }
        model.setCurrentTheme(model.getCurrentInteraction() != null ? model.getCurrentInteraction().theme() : null);

        modelChanged(model, this);
        model.fireInteractionChosenFromHistory(this);
    }

    private void setLabel(Model model) {
        historyLabel.setText(format("Question history (%d):", model.getHistory().size()));
    }
}

