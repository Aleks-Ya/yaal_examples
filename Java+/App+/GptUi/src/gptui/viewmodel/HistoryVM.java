package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.model.state.StateModel;
import gptui.model.storage.Interaction;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static java.lang.String.format;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

@Singleton
public class HistoryVM {
    private static final Logger log = LoggerFactory.getLogger(HistoryVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private ViewModelMediator mediator;
    private final HistoryComboBoxFacade historyCbFacade = new HistoryComboBoxFacade();
    private final StateModelFacade stateModelFacade = new StateModelFacade();

    public void historyComboBoxAction() {
        log.trace("historyComboBoxAction");
        stateModelFacade.chooseHistoryCbInteractionAsCurrent();
    }

    public void clickHistoryDeleteButton() {
        log.trace("clickHistoryDeleteButton");
        var oldCurrentInteractionIndex = historyCbFacade.getSelectedItemIndex();
        stateModelFacade.deleteCurrentInteraction();
        stateModelFacade.choosePreviousInteractionAsCurrent(oldCurrentInteractionIndex);
        historyCbFacade.setItems();
        historyCbFacade.selectCurrentInteraction();
        enableDeleteButton();
        mediator.interactionDeleted();
        mediator.currentInteractionChosen();
    }

    void addShortcuts() {
        log.trace("addShortcuts");
        stateModel.addAccelerator(new KeyCodeCombination(UP, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select next Interaction from history");
            historyCbFacade.selectPreviousItem();
        });
        stateModel.addAccelerator(new KeyCodeCombination(DOWN, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("select previous Interaction from history");
            historyCbFacade.selectNextItem();
        });
    }

    void interactionChosenFromHistory() {
        log.trace("interactionChosenFromHistory");
        historyCbFacade.setItems();
        historyCbFacade.selectCurrentInteraction();
        enableDeleteButton();
    }

    void displayCurrentHistory() {
        log.trace("displayCurrentHistory");
        setLabel();
        stateModelFacade.chooseFirstInteractionAsCurrent();
        historyCbFacade.setItems();
        historyCbFacade.selectCurrentInteraction();
        enableDeleteButton();
    }

    void displayCurrentHistoryIfHistoryFiltered() {
        log.trace("themeWasChosen");
        if (stateModelFacade.isHistoryFiltered()) {
            displayCurrentHistory();
        }
    }

    private void enableDeleteButton() {
        properties.historyDeleteButtonDisable.setValue(stateModelFacade.isCurrentInteractionEmpty());
    }

    void setLabel() {
        log.trace("setLabel");
        var historySize = stateModelFacade.getFilteredHistorySize();
        var allInteractionSize = stateModelFacade.getAllInteractionsSize();
        var label = format("Question history (%d/%d):", historySize, allInteractionSize);
        log.trace("Set label: {}", label);
        properties.historyLabelText.setValue(label);
    }

    private class StateModelFacade {
        private static final Logger log = LoggerFactory.getLogger(StateModelFacade.class);

        private void chooseHistoryCbInteractionAsCurrent() {
            log.trace("chooseHistoryCbInteractionAsCurrent");
            var comboBoxCurrentInteraction = historyCbFacade.getSelectedItem();
            var modelCurrentInteraction = stateModel.getCurrentInteraction();
            if (comboBoxCurrentInteraction != null && !Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
                log.debug("setCurrentInteraction from historyComboBox: {}", comboBoxCurrentInteraction);
                stateModel.setCurrentInteractionId(comboBoxCurrentInteraction.id());
                mediator.currentInteractionChosen();
            }
        }

        public void choosePreviousInteractionAsCurrent(int currentInteractionIndex) {
            stateModel.choosePreviousInteractionAsCurrent(currentInteractionIndex);
        }

        private void chooseFirstInteractionAsCurrent() {
            log.trace("chooseFirstInteractionAsCurrent");
            stateModel.chooseFirstInteractionAsCurrent();
        }

        private void deleteCurrentInteraction() {
            log.trace("deleteCurrentInteraction");
            stateModel.deleteCurrentInteraction();
        }

        private Integer getFilteredHistorySize() {
            log.trace("getFilteredHistorySize");
            return stateModel.getFilteredHistory().size();
        }

        private Integer getAllInteractionsSize() {
            log.trace("getAllInteractionsSize");
            return stateModel.getAllInteractions().size();
        }

        private Boolean isCurrentInteractionEmpty() {
            log.trace("isCurrentInteractionEmpty");
            return stateModel.getCurrentInteractionOpt().isEmpty();
        }

        private Boolean isHistoryFiltered() {
            log.trace("isHistoryFiltered");
            return stateModel.isHistoryFilteringEnabled();
        }
    }

    private class HistoryComboBoxFacade {
        private static final Logger log = LoggerFactory.getLogger(HistoryComboBoxFacade.class);

        private Interaction getSelectedItem() {
            log.trace("getSelectedItem");
            return properties.historyCbSelectionModel.getValue().getSelectedItem();
        }

        private Integer getSelectedItemIndex() {
            log.trace("getSelectedItemIndex");
            return properties.historyCbSelectionModel.getValue().getSelectedIndex();
        }

        private void selectPreviousItem() {
            log.trace("selectPreviousItem");
            properties.historyCbSelectionModel.getValue().selectPrevious();
        }

        private void selectNextItem() {
            log.trace("selectNextItem");
            properties.historyCbSelectionModel.getValue().selectNext();
        }

        private void setItems() {
            log.trace("setItems");
            var modelItems = stateModel.getFilteredHistory();
            log.trace("modelItems: {}", modelItems.size());
            var comboBoxItems = properties.historyCbItems.getValue();
            log.trace("comboBoxItems: {}", comboBoxItems.size());
            if (!Objects.equals(modelItems, comboBoxItems)) {
                log.debug("Set items: {}", modelItems.size());
                var oldOnAction = properties.historyCbOnAction.getValue();
                properties.historyCbOnAction.setValue(null);
                properties.historyCbItems.setValue(FXCollections.observableArrayList(modelItems));
                properties.historyCbOnAction.setValue(oldOnAction);
                setLabel();
            }
        }

        private void selectCurrentInteraction() {
            log.trace("selectCurrentInteraction");
            var modelCurrentInteractionOpt = stateModel.getCurrentInteractionOpt();
            var comboBoxCurrentInteraction = properties.historyCbSelectionModel.getValue().getSelectedItem();
            if (!Objects.equals(modelCurrentInteractionOpt.orElse(null), comboBoxCurrentInteraction)) {
                if (modelCurrentInteractionOpt.isPresent()) {
                    var modelCurrentValue = modelCurrentInteractionOpt.get();
                    log.debug("Select interaction: '{}'", modelCurrentValue);
                    var oldOnAction = properties.historyCbOnAction.getValue();
                    properties.historyCbOnAction.setValue(null);
                    properties.historyCbSelectionModel.getValue().select(modelCurrentValue);
                    properties.historyCbOnAction.setValue(oldOnAction);
                } else {
                    log.debug("Clear selection");
                    var oldOnAction = properties.historyCbOnAction.getValue();
                    properties.historyCbOnAction.setValue(null);
                    properties.historyCbSelectionModel.getValue().clearSelection();
                    properties.historyCbOnAction.setValue(oldOnAction);
                }
            } else {
                log.debug("Selection is unchanged: '{}'", modelCurrentInteractionOpt);
            }
        }

    }

    public static class Properties {
        public final StringProperty historyLabelText = new SimpleStringProperty();
        public final ObjectProperty<SingleSelectionModel<Interaction>> historyCbSelectionModel = new SimpleObjectProperty<>();
        public final BooleanProperty historyDeleteButtonDisable = new SimpleBooleanProperty();
        public final ObjectProperty<ObservableList<Interaction>> historyCbItems = new SimpleObjectProperty<>();
        public final ObjectProperty<EventHandler<ActionEvent>> historyCbOnAction = new SimpleObjectProperty<>();
    }
}

