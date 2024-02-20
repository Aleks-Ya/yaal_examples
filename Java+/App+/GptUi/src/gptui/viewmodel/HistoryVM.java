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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static java.lang.String.format;

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

    public void onHistoryComboBoxAction() {
        log.trace("onHistoryComboBoxAction");
        stateModelFacade.chooseHistoryCbInteractionAsCurrent();
    }

    public void onClickHistoryDeleteButton() {
        log.trace("onClickHistoryDeleteButton");
        stateModelFacade.deleteCurrentInteraction();
        historyCbFacade.setItems();
        historyCbFacade.selectCurrentInteraction();
        enableDeleteButton();
        mediator.interactionDeleted();
        mediator.displayCurrentInteraction();
    }

    void displayCurrentInteraction() {
        log.trace("displayCurrentInteraction");
        setLabel();
        historyCbFacade.setItems();
        historyCbFacade.selectCurrentInteraction();
        enableDeleteButton();
    }

    void selectPreviousItem() {
        log.trace("selectPreviousItem");
        historyCbFacade.selectPreviousItem();
    }

    void selectNextItem() {
        log.trace("selectNextItem");
        historyCbFacade.selectNextItem();
    }

    private void enableDeleteButton() {
        properties.historyDeleteButtonDisable.setValue(stateModelFacade.isCurrentInteractionEmpty());
    }

    private void setLabel() {
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
                mediator.displayCurrentInteraction();
            }
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
            return stateModel.getFullHistory().size();
        }

        private Boolean isCurrentInteractionEmpty() {
            log.trace("isCurrentInteractionEmpty");
            return stateModel.getCurrentInteractionOpt().isEmpty();
        }
    }

    private class HistoryComboBoxFacade {
        private static final Logger log = LoggerFactory.getLogger(HistoryComboBoxFacade.class);

        private Interaction getSelectedItem() {
            log.trace("getSelectedItem");
            return properties.historyCbSelectionModel.getValue().getSelectedItem();
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

