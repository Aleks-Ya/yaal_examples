package gptui.ui.view;

import gptui.storage.Interaction;
import gptui.ui.viewmodel.HistoryVM;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @FXML
    private Label historyLabel;
    @FXML
    private ComboBox<Interaction> historyComboBox;
    @FXML
    private Button historyDeleteButton;
    @Inject
    private HistoryVM vm;

    @FXML
    void historyComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("historyComboBoxAction");
        vm.historyComboBoxAction();
    }

    @FXML
    void clickHistoryDeleteButton(ActionEvent ignoredEvent) {
        log.trace("clickHistoryDeleteButton");
        vm.clickHistoryDeleteButton();
    }

    @Override
    protected void initialize() {
        vm.properties.historyLabelText.bindBidirectional(historyLabel.textProperty());
        vm.properties.historyCbSelectionModel.bindBidirectional(historyComboBox.selectionModelProperty());
        vm.properties.historyCbItems.bindBidirectional(historyComboBox.itemsProperty());
        vm.properties.historyCbOnAction.bindBidirectional(historyComboBox.onActionProperty());
        vm.properties.historyDeleteButtonDisable.bindBidirectional(historyDeleteButton.disableProperty());
    }
}

