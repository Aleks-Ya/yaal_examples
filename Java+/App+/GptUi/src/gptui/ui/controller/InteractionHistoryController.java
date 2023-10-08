package gptui.ui.controller;

import gptui.storage.Interaction;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class InteractionHistoryController extends BaseController {
    @FXML
    private ComboBox<Interaction> interactionHistoryComboBox;

    @Override
    public void modelChanged(Model model, EventSource source) {
        var modelItems = FXCollections.observableArrayList(model.getInteractionHistory());
        var comboBoxItems = interactionHistoryComboBox.getItems();
        if (!Objects.equals(modelItems, comboBoxItems)) {
            Platform.runLater(() -> interactionHistoryComboBox.setItems(modelItems));
        }
        var modelCurrentValue = model.getCurrentInteraction();
        var comboBoxCurrentValue = interactionHistoryComboBox.getSelectionModel().getSelectedItem();
        if (!Objects.equals(modelCurrentValue, comboBoxCurrentValue)) {
            Platform.runLater(() -> interactionHistoryComboBox.getSelectionModel().select(modelCurrentValue));
        }
    }

    @FXML
    void interactionHistoryComboBoxAction(ActionEvent ignoredEvent) {
        var modelCurrentInteraction = model.getCurrentInteraction();
        var comboBoxCurrentInteraction = interactionHistoryComboBox.getSelectionModel().getSelectedItem();
        if (!Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction) && comboBoxCurrentInteraction != null) {
            model.setCurrentInteraction(comboBoxCurrentInteraction);
            model.fireInteractionChosenFromHistory(this);
        }
    }
}

