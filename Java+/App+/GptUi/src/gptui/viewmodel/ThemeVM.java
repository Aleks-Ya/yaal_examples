package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.model.state.StateModel;
import gptui.model.storage.Interaction;
import gptui.model.storage.Theme;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static gptui.viewmodel.CbHelper.updateCbSilently;

@Singleton
public class ThemeVM {
    private static final Logger log = LoggerFactory.getLogger(ThemeVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private ViewModelMediator mediator;

    public void onThemeComboBoxAction() {
        log.trace("onThemeComboBoxAction");
        chooseThemeFromCb();
    }

    public void onThemeFilterHistoryCheckBoxClicked() {
        log.trace("onThemeFilterHistoryCheckBoxClicked");
        var cbValue = properties.filterHistoryCheckBoxSelected.getValue();
        var modelValue = stateModel.isHistoryFilteringEnabled();
        log.trace("cbValue={}, modelValue={}", cbValue, modelValue);
        if (cbValue != modelValue) {
            log.trace("Setting ThemeFilterHistoryCheckBox to {}", cbValue);
            stateModel.setIsHistoryFilteringEnabled(cbValue);
            mediator.isThemeFilterHistoryChanged();
        }
    }

    public void addNewTheme(String theme) {
        log.trace("addNewTheme");
        var newTheme = stateModel.addTheme(theme);
        stateModel.setCurrentTheme(newTheme);
        mediator.themeWasChosen();
    }

    void updateComboBoxSelectedItemFromCurrentInteraction() {
        var themeTitle = stateModel.getCurrentInteractionOpt()
                .map(Interaction::themeId)
                .map(stateModel::getTheme)
                .orElse(null);
        stateModel.setCurrentTheme(themeTitle);
        updateCbSilently(() -> properties.themeCbValue.setValue(themeTitle), properties.themeCbOnAction);
    }

    void updateComboBoxSelectedItemFromStateModel() {
        updateCbSilently(() -> properties.themeCbValue.setValue(stateModel.getCurrentTheme()), properties.themeCbOnAction);
    }

    void updateComboBoxItems() {
        var currentModelItems = FXCollections.observableArrayList(stateModel.getThemes());
        var currentComboBoxItems = properties.themeCbItems.getValue();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            log.trace("Set themeCbItems: {}", currentModelItems);
            updateCbSilently(() -> properties.themeCbItems.setValue(currentModelItems),
                    properties.themeCbOnAction);
            setLabel();
        }
    }

    void setLabel() {
        properties.themeLabelText.setValue(String.format("_Theme (%d):", stateModel.getThemes().size()));
    }

    void initialize() {
        properties.themeCbCellFactory.setValue(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Theme item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item + " (" + stateModel.getInteractionCountInTheme(item.title()) + ")");
                }
            }
        });
    }

    private void chooseThemeFromCb() {
        log.trace("chooseThemeFromCb");
        var currentComboBoxValue = properties.themeCbValue.getValue();
        log.trace("currentComboBoxValue: '{}'", currentComboBoxValue);
        var currentModelValue = stateModel.getCurrentTheme();
        log.trace("currentModelValue: '{}'", currentModelValue);
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            stateModel.setCurrentTheme(currentComboBoxValue);
            mediator.themeWasChosen();
        }
    }

    public static class Properties {
        public final ObjectProperty<Theme> themeCbValue = new SimpleObjectProperty<>();
        public final ListProperty<Theme> themeCbItems = new SimpleListProperty<>();
        public final StringProperty themeCbEditor = new SimpleStringProperty();
        public final ObjectProperty<EventHandler<ActionEvent>> themeCbOnAction = new SimpleObjectProperty<>();
        public final ObjectProperty<Callback<ListView<Theme>, ListCell<Theme>>> themeCbCellFactory = new SimpleObjectProperty<>();
        public final BooleanProperty filterHistoryCheckBoxSelected = new SimpleBooleanProperty();
        public final StringProperty themeLabelText = new SimpleStringProperty();
    }
}

