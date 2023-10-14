package gptui.ui.controller;

import gptui.format.ThemeHelper;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.Objects;

import static java.lang.String.format;

public class InteractionHistoryController extends BaseController {
    @Inject
    private GptStorage storage;
    @FXML
    private Label interactionHistoryLabel;
    @FXML
    private ComboBox<Interaction> interactionHistoryComboBox;
    @FXML
    private Button deleteInteractionButton;
    @Inject
    private ThemeHelper themeHelper;

    @Override
    public void modelChanged(Model model, EventSource source) {
        var modelItems = FXCollections.observableArrayList(model.getInteractionHistory());
        var comboBoxItems = interactionHistoryComboBox.getItems();
        if (!Objects.equals(modelItems, comboBoxItems)) {
            Platform.runLater(() -> {
                interactionHistoryComboBox.setItems(modelItems);
                setLabel(model);
            });
        }
        var modelCurrentValue = model.getCurrentInteraction();
        var comboBoxCurrentValue = interactionHistoryComboBox.getSelectionModel().getSelectedItem();
        if (!Objects.equals(modelCurrentValue, comboBoxCurrentValue)) {
            if (modelCurrentValue != null) {
                Platform.runLater(() -> interactionHistoryComboBox.getSelectionModel().select(modelCurrentValue));
            } else {
                Platform.runLater(() -> interactionHistoryComboBox.getSelectionModel().clearSelection());
            }
        }
        deleteInteractionButton.setDisable(model.getCurrentInteraction() == null);
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        Platform.runLater(() -> setLabel(model));
    }

    private void setLabel(Model model) {
        interactionHistoryLabel.setText(format("Question history (%d):", model.getInteractionHistory().size()));
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

    @FXML
    void clickDeleteInteractionButton(ActionEvent ignoredEvent) {
        var oldInteractionHistory = model.getInteractionHistory();
        var oldCurrentInteraction = model.getCurrentInteraction();
        var oldCurrentInteractionIndex = oldInteractionHistory.indexOf(oldCurrentInteraction);

        storage.deleteInteraction(oldCurrentInteraction.id());

        var newInteractionHistory = storage.readAllInteractions();
        model.setInteractionHistory(newInteractionHistory);
        model.setThemeList(themeHelper.interactionsToThemeList(newInteractionHistory));

        if (!newInteractionHistory.isEmpty()) {
            var newCurrentInteractionIndex = oldCurrentInteractionIndex - 1;
            Interaction newCurrentInteraction;
            if (newCurrentInteractionIndex >= 0 && newCurrentInteractionIndex < newInteractionHistory.size()) {
                newCurrentInteraction = newInteractionHistory.get(newCurrentInteractionIndex);
            } else {
                newCurrentInteraction = newInteractionHistory.get(newInteractionHistory.size() - 1);
            }
            model.setCurrentInteraction(newCurrentInteraction);
        } else {
            model.setCurrentInteraction(null);
        }
        model.setCurrentTheme(model.getCurrentInteraction() != null ? model.getCurrentInteraction().theme() : null);

        modelChanged(model, this);
        model.fireInteractionChosenFromHistory(this);
    }
}

