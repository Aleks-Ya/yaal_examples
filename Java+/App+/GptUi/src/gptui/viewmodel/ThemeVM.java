package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.model.event.EventListener;
import gptui.model.event.EventModel;
import gptui.model.event.EventSource;
import gptui.model.state.StateModel;
import gptui.model.storage.Interaction;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Singleton
public class ThemeVM implements EventSource, EventListener {
    private static final Logger log = LoggerFactory.getLogger(ThemeVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private EventModel eventModel;

    @Inject
    public void init() {
        eventModel.subscribe(this);
    }

    public void themeComboBoxKeyReleased() {
        log.trace("themeComboBoxKeyReleased");
        stateModel.setCurrentTheme(properties.themeCbEditor.getValue());
    }

    public void themeComboBoxAction() {
        log.trace("themeComboBoxAction");
        var currentComboBoxValue = properties.themeCbValue.getValue();
        log.trace("currentComboBoxValue: '{}'", currentComboBoxValue);
        var currentModelValue = stateModel.getCurrentTheme();
        log.trace("currentModelValue: '{}'", currentModelValue);
        if (!Objects.equals(currentComboBoxValue, currentModelValue)) {
            stateModel.setCurrentTheme(currentComboBoxValue);
            eventModel.fire().themeWasChosen();
        }
    }

    public void themeFilterHistoryCheckBoxClicked() {
        log.trace("themeFilterHistoryCheckBoxClicked");
        if (properties.filterHistoryCheckBoxSelected.getValue() != stateModel.isHistoryFilteringEnabled()) {
            stateModel.setIsHistoryFilteringEnabled(properties.filterHistoryCheckBoxSelected.getValue());
            log.debug("ThemeFilterHistoryCheckBox is set to {}", stateModel.isHistoryFilteringEnabled());
            eventModel.fire().isThemeFilterHistoryChanged();
        }
    }

    @Override
    public void stageWasShowed() {
        log.trace("stageWasShowed");
        setLabel();
    }

    @Override
    public void interactionChosenFromHistory() {
        log.trace("interactionChosenFromHistory");
        updateComboBoxItems();
        updateComboBoxCurrentValue();
    }

    @Override
    public void interactionDeleted() {
        updateComboBoxItems();
    }

    @Override
    public void interactionCreated() {
        updateComboBoxItems();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    private void updateComboBoxCurrentValue() {
        stateModel.getCurrentInteractionOpt()
                .map(Interaction::theme)
                .ifPresent(properties.themeCbValue::setValue);
    }

    private void updateComboBoxItems() {
        var currentModelItems = FXCollections.observableArrayList(stateModel.getThemes());
        var currentComboBoxItems = properties.themeCbItems.getValue();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            properties.themeCbItems.setValue(currentModelItems);
            setLabel();
        }
    }

    private void setLabel() {
        properties.themeLabelText.setValue(String.format("Theme (%d):", stateModel.getThemes().size()));
    }

    public static class Properties {
        public final StringProperty themeCbValue = new SimpleStringProperty();
        public final ListProperty<String> themeCbItems = new SimpleListProperty<>();
        public final StringProperty themeCbEditor = new SimpleStringProperty();
        public final BooleanProperty filterHistoryCheckBoxSelected = new SimpleBooleanProperty();
        public final StringProperty themeLabelText = new SimpleStringProperty();
    }
}
