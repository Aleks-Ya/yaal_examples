package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.model.state.StateModel;
import gptui.model.storage.Interaction;
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

    public void onThemeComboBoxKeyReleased() {
        log.trace("onThemeComboBoxKeyReleased");
        stateModel.setCurrentTheme(properties.themeCbEditor.getValue());
    }

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

    void updateComboBoxCurrentValue() {
        var theme = stateModel.getCurrentInteractionOpt().map(Interaction::theme).orElse(null);
        stateModel.setCurrentTheme(theme);
        updateCbSilently(() -> properties.themeCbValue.setValue(theme), properties.themeCbOnAction);
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
        properties.themeLabelText.setValue(String.format("Theme (%d):", stateModel.getThemes().size()));
    }

    void initialize() {
        properties.themeCbCellFactory.setValue(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item + " (" + stateModel.getInteractionCountInTheme(item) + ")");
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
        var themeTitle = currentModelValue != null ? currentModelValue.title() : null;
        if (!Objects.equals(currentComboBoxValue, themeTitle)) {
            stateModel.setCurrentTheme(currentComboBoxValue);
            mediator.themeWasChosen();
        }
    }

    public static class Properties {
        public final StringProperty themeCbValue = new SimpleStringProperty();
        public final ListProperty<String> themeCbItems = new SimpleListProperty<>();
        public final StringProperty themeCbEditor = new SimpleStringProperty();
        public final ObjectProperty<EventHandler<ActionEvent>> themeCbOnAction = new SimpleObjectProperty<>();
        public final ObjectProperty<Callback<ListView<String>, ListCell<String>>> themeCbCellFactory = new SimpleObjectProperty<>();
        public final BooleanProperty filterHistoryCheckBoxSelected = new SimpleBooleanProperty();
        public final StringProperty themeLabelText = new SimpleStringProperty();
    }
}

