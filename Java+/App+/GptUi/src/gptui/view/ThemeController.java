package gptui.view;

import gptui.viewmodel.ThemeVM;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ThemeController.class);
    @FXML
    private Label themeLabel;
    @FXML
    private ComboBox<String> themeComboBox;
    @FXML
    private CheckBox filterHistoryCheckBox;
    @Inject
    private ThemeVM vm;

    @FXML
    void themeComboBoxKeyReleased(KeyEvent ignoredEvent) {
        log.trace("themeComboBoxKeyReleased");
        vm.onThemeComboBoxKeyReleased();
    }

    @FXML
    void themeComboBoxAction(ActionEvent ignoredEvent) {
        log.trace("themeComboBoxAction");
        vm.onThemeComboBoxAction();
    }

    @FXML
    void themeFilterHistoryCheckBoxClicked(ActionEvent ignore) {
        log.trace("themeFilterHistoryCheckBoxClicked");
        vm.onThemeFilterHistoryCheckBoxClicked();
    }

    @Override
    protected void initialize() {
        vm.properties.themeLabelText.bindBidirectional(themeLabel.textProperty());
        vm.properties.themeCbValue.bindBidirectional(themeComboBox.valueProperty());
        vm.properties.themeCbItems.bindBidirectional(themeComboBox.itemsProperty());
        vm.properties.themeCbEditor.bindBidirectional(themeComboBox.getEditor().textProperty());
        vm.properties.themeCbOnAction.bindBidirectional(themeComboBox.onActionProperty());
        vm.properties.filterHistoryCheckBoxSelected.bindBidirectional(filterHistoryCheckBox.selectedProperty());
    }
}
