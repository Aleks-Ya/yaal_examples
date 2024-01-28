package gptui.ui.controller;

import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionStorage;
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

import jakarta.inject.Inject;
import java.util.List;
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
    private InteractionStorage storage;
    @FXML
    private Label historyLabel;
    @FXML
    private ComboBox<Interaction> historyComboBox;
    @FXML
    private Button historyDeleteButton;

    @FXML
    void historyComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("historyComboBoxAction");
        var modelCurrentInteraction = storage.readInteraction(model.getCurrentInteractionId()).orElseThrow();
        var comboBoxCurrentInteraction = historyComboBox.getSelectionModel().getSelectedItem();
        if (comboBoxCurrentInteraction != null && !Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
            log.debug("setCurrentInteraction from historyComboBox: {}", comboBoxCurrentInteraction);
            model.setCurrentInteractionId(comboBoxCurrentInteraction.id());
            model.fire().interactionChosenFromHistory(this);
        }
    }

    @FXML
    void clickHistoryDeleteButton(ActionEvent ignoredEvent) {
        log.trace("clickHistoryDeleteButton");
        var oldCurrentInteractionIndex = historyComboBox.getSelectionModel().getSelectedIndex();
        storage.deleteInteraction(model.getCurrentInteractionId());

        updateHistoryItemsInModel(oldCurrentInteractionIndex);
        setHistoryComboBoxItems(model.getFilteredHistory());
        selectCurrentInteractionInHistoryComboBox(model.getCurrentInteractionId());
        enableDeleteButton(model.getCurrentInteractionId());
        model.fire().interactionsUpdated(this);
        model.fire().interactionChosenFromHistory(this);
    }

    @Override
    public void interactionsUpdated(Model model) {
        log.trace("interactionsUpdated");
        setLabel(model.getFilteredHistory().size());
        setHistoryComboBoxItems(model.getFilteredHistory());
        enableDeleteButton(model.getCurrentInteractionId());
    }

    @Override
    public void answerUpdated(Model model) {
        log.trace("answerUpdated");
        updateAll(model);
        model.fire().interactionsUpdated(this);
    }

    @Override
    public void stageWasShowed(Model model) {
        log.trace("stageWasShowed");
        setLabel(model.getFilteredHistory().size());
        model.addAccelerator(new KeyCodeCombination(UP, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select next Interaction from history");
            historyComboBox.getSelectionModel().selectPrevious();
        });
        model.addAccelerator(new KeyCodeCombination(DOWN, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select previous Interaction from history");
            historyComboBox.getSelectionModel().selectNext();
        });
    }

    @Override
    public void interactionChosenFromHistory(Model model) {
        log.trace("interactionChosenFromHistory");
        actualizeCurrentInteractionInModel(model);
        selectCurrentInteractionInHistoryComboBox(model.getCurrentInteractionId());
    }

    @Override
    public void isThemeFilterHistoryChanged(Model model) {
        log.trace("isThemeFilterHistoryChanged");
        updateAll(model);
    }

    private void updateAll(Model model) {
        setLabel(model.getFilteredHistory().size());
        actualizeCurrentInteractionInModel(model);
        setHistoryComboBoxItems(model.getFilteredHistory());
        selectCurrentInteractionInHistoryComboBox(model.getCurrentInteractionId());
        enableDeleteButton(model.getCurrentInteractionId());
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    private void updateHistoryItemsInModel(int oldCurrentInteractionIndex) {
        var newHistory = model.getFilteredHistory();
        if (!newHistory.isEmpty()) {
            var newCurrentInteractionIndex = oldCurrentInteractionIndex - 1;
            Interaction newCurrentInteraction;
            if (newCurrentInteractionIndex >= 0 && newCurrentInteractionIndex < newHistory.size()) {
                newCurrentInteraction = newHistory.get(newCurrentInteractionIndex);
            } else {
                newCurrentInteraction = newHistory.getLast();
            }
            model.setCurrentInteractionId(newCurrentInteraction.id());
            model.setCurrentTheme(newCurrentInteraction.theme());
        } else {
            model.setCurrentInteractionId(null);
            model.setCurrentTheme(null);
        }
    }

    private void enableDeleteButton(InteractionId currentInteractionId) {
        historyDeleteButton.setDisable(currentInteractionId == null);
    }

    private void actualizeCurrentInteractionInModel(Model model) {
        if (!model.getFilteredHistory().isEmpty()) {
            model.setCurrentInteractionId(model.getFilteredHistory().getFirst().id());
        } else {
            model.setCurrentInteractionId(null);
        }
    }

    private void setHistoryComboBoxItems(List<Interaction> modelItems) {
        log.trace("modelItems: {}", modelItems.size());
        var comboBoxItems = historyComboBox.getItems();
        log.trace("comboBoxItems: {}", comboBoxItems.size());
        if (!Objects.equals(modelItems, comboBoxItems)) {
            log.debug("Set items: {}", modelItems.size());
            updateSilently(historyComboBox, comboBox -> comboBox.setItems(FXCollections.observableArrayList(modelItems)));
            setLabel(modelItems.size());
        }
    }

    private void selectCurrentInteractionInHistoryComboBox(InteractionId currentInteractionId) {
        var modelCurrentValueOpt = storage.readInteraction(currentInteractionId);
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
    }

    private void setLabel(int historySize) {
        var label = format("Question history (%d/%d):", historySize, storage.readAllInteractions().size());
        log.trace("Set label: {}", label);
        historyLabel.setText(label);
    }
}

