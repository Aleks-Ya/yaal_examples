package gptui.viewmodel;

import com.google.inject.Singleton;
import gptui.LogUtils;
import gptui.model.storage.Interaction;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SingleSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static gptui.LogUtils.shorten;
import static gptui.viewmodel.CbHelper.updateCbSilently;
import static java.lang.String.format;
import static javafx.collections.FXCollections.observableArrayList;

@Singleton
public class HistoryVM {
    private static final Logger log = LoggerFactory.getLogger(HistoryVM.class);
    public final Properties properties = new Properties();
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
            var modelCurrentInteraction = mediator.getCurrentInteraction();
            if (comboBoxCurrentInteraction != null && !Objects.equals(modelCurrentInteraction, comboBoxCurrentInteraction)) {
                log.debug("setCurrentInteraction from historyComboBox: {}", shorten(comboBoxCurrentInteraction));
                mediator.setCurrentInteractionId(comboBoxCurrentInteraction.id());
                mediator.displayCurrentInteraction();
            }
        }

        private void deleteCurrentInteraction() {
            log.trace("deleteCurrentInteraction");
            mediator.deleteCurrentInteraction();
        }

        private Integer getFilteredHistorySize() {
            log.trace("getFilteredHistorySize");
            return mediator.getFilteredHistory().size();
        }

        private Integer getAllInteractionsSize() {
            log.trace("getAllInteractionsSize");
            return mediator.getFullHistory().size();
        }

        private Boolean isCurrentInteractionEmpty() {
            log.trace("isCurrentInteractionEmpty");
            return mediator.getCurrentInteractionOpt().isEmpty();
        }
    }

    private class HistoryComboBoxFacade {
        private static final Logger log = LoggerFactory.getLogger(HistoryComboBoxFacade.class);

        private Interaction getSelectedItem() {
            log.trace("getSelectedItem");
            return properties.historyCbSelectionModel.getValue().getSelectedItem().interaction();
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
            var modelItems = mediator.getFilteredHistory();
            log.trace("modelItems: {}", modelItems.size());
            var comboBoxItems = properties.historyCbItems.getValue();
            log.trace("comboBoxItems: {}", comboBoxItems.size());
            var comboBoxItemInteractions = comboBoxItems.stream().map(InteractionItem::interaction).toList();
            if (!Objects.equals(modelItems, comboBoxItemInteractions)) {
                log.debug("Set items: {}", modelItems.size());
                var interactionItems = modelItems.stream()
                        .map(interaction -> new InteractionItem(mediator.getTheme(interaction.themeId()), interaction))
                        .toList();
                updateCbSilently(() -> properties.historyCbItems.setValue(observableArrayList(interactionItems)),
                        properties.historyCbOnAction);
            }
        }

        private void selectCurrentInteraction() {
            log.trace("selectCurrentInteraction");
            var modelCurrentInteractionIdOpt = mediator.getCurrentInteractionOpt();
            var comboBoxCurrentInteraction = properties.historyCbSelectionModel.getValue().getSelectedItem();
            var cmCurrentInteraction = comboBoxCurrentInteraction != null ? comboBoxCurrentInteraction.interaction() : null;
            if (!Objects.equals(modelCurrentInteractionIdOpt.orElse(null), cmCurrentInteraction)) {
                if (modelCurrentInteractionIdOpt.isPresent()) {
                    var modelCurrentValue = modelCurrentInteractionIdOpt.get();
                    log.debug("Select interaction: '{}'", shorten(modelCurrentValue));
                    var interactionItem = new InteractionItem(mediator.getCurrentTheme(), modelCurrentValue);
                    updateCbSilently(() -> properties.historyCbSelectionModel.getValue().select(interactionItem),
                            properties.historyCbOnAction);
                } else {
                    log.debug("Clear selection");
                    updateCbSilently(() -> properties.historyCbSelectionModel.getValue().clearSelection(),
                            properties.historyCbOnAction);
                }
            } else {
                log.debug("Selection is unchanged: '{}'", modelCurrentInteractionIdOpt.map(LogUtils::shorten));
            }
        }
    }

    public static class Properties {
        public final StringProperty historyLabelText = new SimpleStringProperty();
        public final ObjectProperty<SingleSelectionModel<InteractionItem>> historyCbSelectionModel = new SimpleObjectProperty<>();
        public final BooleanProperty historyDeleteButtonDisable = new SimpleBooleanProperty();
        public final ObjectProperty<ObservableList<InteractionItem>> historyCbItems = new SimpleObjectProperty<>();
        public final ObjectProperty<EventHandler<ActionEvent>> historyCbOnAction = new SimpleObjectProperty<>();
    }
}

