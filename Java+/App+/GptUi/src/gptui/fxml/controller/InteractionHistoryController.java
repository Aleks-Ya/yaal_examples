package gptui.fxml.controller;

import gptui.fxml.Model;
import gptui.storage.Interaction;
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
    public void modelChanged(Model model) {
        Platform.runLater(() -> {
            var modelItems = FXCollections.observableArrayList(model.getInteractionHistory());
            var comboBoxItems = interactionHistoryComboBox.getItems();
            if (!Objects.equals(modelItems, comboBoxItems)) {
                interactionHistoryComboBox.setItems(modelItems);
            }
            var modelCurrentValue = model.getCurrentInteraction();
            var comboBoxCurrentValue = this.interactionHistoryComboBox.getValue();
            if (!Objects.equals(modelCurrentValue, comboBoxCurrentValue)) {
                interactionHistoryComboBox.setValue(modelCurrentValue);
            }
        });
    }

    @FXML
    void interactionHistoryComboBoxAction(ActionEvent ignoredEvent) {
        var modelCurrentInteraction = model.getCurrentInteraction();
        var comboBoxCurrentInteraction = interactionHistoryComboBox.getValue();
        if (!Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
            model.setCurrentInteraction(comboBoxCurrentInteraction);
            model.fireInteractionChosenFromHistory();
        }
    }
}

