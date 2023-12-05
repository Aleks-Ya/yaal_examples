package gptui.ui.controller;

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
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;

import static gptui.ui.controller.ComboBoxUpdater.updateSilently;
import static java.lang.String.format;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

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

    @Override
    public void modelChanged(Model model) {
        log.trace("modelChanged");
        var modelItems = FXCollections.observableArrayList(model.getHistory().stream()
                .map(interactionId -> storage.readInteraction(interactionId).orElseThrow())
                .filter(interaction -> !model.getThemeFilterHistory() || model.getCurrentTheme().trim().equals(interaction.theme().trim()))
                .toList());
        var comboBoxItems = historyComboBox.getItems();
        if (!Objects.equals(modelItems, comboBoxItems)) {
            log.debug("Set items");
            updateSilently(historyComboBox, comboBox -> comboBox.setItems(modelItems));
            setLabel(model);
        }
        var modelCurrentValueOpt = storage.readInteraction(model.getCurrentInteractionId());
        var comboBoxCurrentValue = historyComboBox.getSelectionModel().getSelectedItem();
        if (!Objects.equals(modelCurrentValueOpt.orElse(null), comboBoxCurrentValue)) {
            if (modelCurrentValueOpt.isPresent()) {
                var modelCurrentValue = modelCurrentValueOpt.get();
                log.debug("Select interaction: {}", modelCurrentValue);
                updateSilently(historyComboBox, comboBox -> comboBox.getSelectionModel().select(modelCurrentValue));
            } else {
                log.debug("Clear selection");
                updateSilently(historyComboBox, comboBox -> comboBox.getSelectionModel().clearSelection());
            }
        } else {
            log.debug("Selection is unchanged: {}", modelCurrentValueOpt);
        }
        historyDeleteButton.setDisable(model.getCurrentInteractionId() == null);
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        setLabel(model);
        model.addAccelerator(new KeyCodeCombination(UP, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select next Interaction from history");
            historyComboBox.getSelectionModel().selectPrevious();
        });
        model.addAccelerator(new KeyCodeCombination(DOWN, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select previous Interaction from history");
            historyComboBox.getSelectionModel().selectNext();
        });
    }

    @FXML
    void historyComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("historyComboBoxAction");
        var modelCurrentInteraction = storage.readInteraction(model.getCurrentInteractionId()).orElseThrow();
        var comboBoxCurrentInteraction = historyComboBox.getSelectionModel().getSelectedItem();
        if (comboBoxCurrentInteraction != null && !Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
            log.debug("setCurrentInteraction from historyComboBox: {}", comboBoxCurrentInteraction);
            model.setCurrentInteractionId(comboBoxCurrentInteraction.id());
            model.fireInteractionChosenFromHistory(this);
        }
    }

    @FXML
    void clickHistoryDeleteButton(ActionEvent ignoredEvent) {
        log.trace("clickHistoryDeleteButton");
        var oldCurrentInteractionIndex = historyComboBox.getSelectionModel().getSelectedIndex();
        storage.deleteInteraction(model.getCurrentInteractionId());


        var newHistory = storage.readAllInteractions();
        model.setHistory(newHistory);

        if (!newHistory.isEmpty()) {
            var newCurrentInteractionIndex = oldCurrentInteractionIndex - 1;
            Interaction newCurrentInteraction;
            if (newCurrentInteractionIndex >= 0 && newCurrentInteractionIndex < newHistory.size()) {
                newCurrentInteraction = newHistory.get(newCurrentInteractionIndex);
            } else {
                newCurrentInteraction = newHistory.get(newHistory.size() - 1);
            }
            model.setCurrentInteractionId(newCurrentInteraction.id());
        } else {
            model.setCurrentInteractionId(null);
        }
        model.setCurrentTheme(model.getCurrentInteractionId() != null
                ? storage.readInteraction(model.getCurrentInteractionId()).orElseThrow().theme() : null);

        modelChanged(model);
        model.fireInteractionChosenFromHistory(this);
    }

    private void setLabel(Model model) {
        historyLabel.setText(format("Question history (%d):", model.getHistory().size()));
    }
}

