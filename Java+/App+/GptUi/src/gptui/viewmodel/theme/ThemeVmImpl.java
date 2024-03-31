package gptui.viewmodel.theme;

import com.google.inject.Singleton;
import gptui.model.storage.Interaction;
import gptui.model.storage.Theme;
import gptui.viewmodel.mediator.ThemeMediator;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static gptui.viewmodel.CbHelper.updateCbSilently;

@Singleton
class ThemeVmImpl implements ThemeVmController, ThemeVmMediator {
    private static final Logger log = LoggerFactory.getLogger(ThemeVmImpl.class);
    public final ThemeVmProperties properties = new ThemeVmProperties();
    @Inject
    private ThemeMediator mediator;

    @Override
    public void onThemeComboBoxAction() {
        log.trace("onThemeComboBoxAction");
        chooseThemeFromCb();
    }

    @Override
    public void onThemeFilterHistoryCheckBoxClicked() {
        log.trace("onThemeFilterHistoryCheckBoxClicked");
        var cbValue = properties.filterHistoryCheckBoxSelected.getValue();
        var modelValue = mediator.isHistoryFilteringEnabled();
        log.trace("cbValue={}, modelValue={}", cbValue, modelValue);
        if (cbValue != modelValue) {
            log.trace("Setting ThemeFilterHistoryCheckBox to {}", cbValue);
            mediator.setIsHistoryFilteringEnabled(cbValue);
            mediator.isThemeFilterHistoryChanged();
        }
    }

    @Override
    public ThemeVmProperties properties() {
        return properties;
    }

    @Override
    public void addNewTheme(String theme) {
        log.trace("addNewTheme");
        var newTheme = mediator.addTheme(theme);
        mediator.setCurrentTheme(newTheme);
        mediator.themeWasChosen();
    }

    @Override
    public void updateComboBoxSelectedItemFromCurrentInteraction() {
        var themeTitle = mediator.getCurrentInteractionOpt()
                .map(Interaction::themeId)
                .map(mediator::getTheme)
                .orElse(null);
        mediator.setCurrentTheme(themeTitle);
        updateCbSilently(() -> properties.themeCbValue.setValue(themeTitle), properties.themeCbOnAction);
    }

    @Override
    public void updateComboBoxSelectedItemFromStateModel() {
        updateCbSilently(() -> properties.themeCbValue.setValue(mediator.getCurrentTheme()), properties.themeCbOnAction);
    }

    @Override
    public void updateComboBoxItems() {
        var currentModelItems = FXCollections.observableArrayList(mediator.getThemes());
        var currentComboBoxItems = properties.themeCbItems.getValue();
        if (!Objects.equals(currentModelItems, currentComboBoxItems)) {
            log.trace("Set themeCbItems: {}", currentModelItems);
            updateCbSilently(() -> properties.themeCbItems.setValue(currentModelItems),
                    properties.themeCbOnAction);
            setLabel();
        }
    }

    @Override
    public void setLabel() {
        properties.themeLabelText.setValue(String.format("_Theme (%d):", mediator.getThemes().size()));
    }

    @Override
    public void initialize() {
        properties.themeCbCellFactory.setValue(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Theme item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item + " (" + mediator.getInteractionCountInTheme(item.title()) + ")");
                }
            }
        });
    }

    private void chooseThemeFromCb() {
        log.trace("chooseThemeFromCb");
        var currentComboBoxValue = properties.themeCbValue.getValue();
        log.trace("currentComboBoxValue: '{}'", currentComboBoxValue);
        var currentModelValue = mediator.getCurrentTheme();
        log.trace("currentModelValue: '{}'", currentModelValue);
        if (currentComboBoxValue != null && !Objects.equals(currentComboBoxValue, currentModelValue)) {
            mediator.setCurrentTheme(currentComboBoxValue);
            mediator.themeWasChosen();
        }
    }
}

