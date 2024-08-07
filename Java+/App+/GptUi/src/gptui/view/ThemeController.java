package gptui.view;

import gptui.model.storage.Theme;
import gptui.viewmodel.theme.ThemeVmController;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import org.controlsfx.control.SearchableComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThemeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ThemeController.class);
    @FXML
    private Label themeLabel;
    @FXML
    private SearchableComboBox<Theme> themeComboBox;
    @FXML
    private CheckBox filterHistoryCheckBox;
    @FXML
    private Button addButton;
    @Inject
    private ThemeVmController vm;
    private final TextInputDialog newThemeDialog = new TextInputDialog();

    @FXML
    void themeFilterHistoryCheckBoxClicked(ActionEvent ignore) {
        log.trace("themeFilterHistoryCheckBoxClicked");
        vm.onThemeFilterHistoryCheckBoxClicked();
    }

    @FXML
    void onAddButtonClicked(ActionEvent ignore) {
        log.trace("onAddButtonClicked");
        vm.onThemeFilterHistoryCheckBoxClicked();
    }

    @Override
    protected void initialize() {
        vm.properties().themeLabelText.bindBidirectional(themeLabel.textProperty());
        vm.properties().themeCbValue.bindBidirectional(themeComboBox.valueProperty());
        vm.properties().themeCbItems.bindBidirectional(themeComboBox.itemsProperty());
        vm.properties().themeCbEditor.bindBidirectional(themeComboBox.getEditor().textProperty());
        vm.properties().themeCbCellFactory.bindBidirectional(themeComboBox.cellFactoryProperty());
        vm.properties().filterHistoryCheckBoxSelected.bindBidirectional(filterHistoryCheckBox.selectedProperty());

        newThemeDialog.setTitle("Add new theme");
        newThemeDialog.setHeaderText("New theme:");
        addButton.setOnAction(event -> {
            newThemeDialog.show();
            newThemeDialog.getEditor().clear();
            newThemeDialog.getEditor().requestFocus();
            newThemeDialog.hide();
            newThemeDialog.showAndWait().ifPresent(theme -> vm.addNewTheme(theme));
        });
        themeLabel.setLabelFor(themeComboBox);
        themeComboBox.showingProperty().addListener((observable, oldValue, newValue) -> vm.onThemeComboBoxAction());
    }
}
