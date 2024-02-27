package gptui.viewmodel;

import gptui.model.state.StateModel;
import gptui.model.storage.AnswerType;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

@Singleton
class ViewModelMediator {
    private static final Logger log = LoggerFactory.getLogger(ViewModelMediator.class);
    @Inject
    @Named(ViewModelModule.GRAMMAR)
    private AnswerVM grammarAnswerVM;
    @Inject
    @Named(ViewModelModule.SHORT)
    private AnswerVM shortAnswerVM;
    @Inject
    @Named(ViewModelModule.LONG)
    private AnswerVM longAnswerVM;
    @Inject
    @Named(ViewModelModule.GCP)
    private AnswerVM gcpAnswerVM;
    @Inject
    private HistoryVM historyVM;
    @Inject
    private QuestionVM questionVM;
    @Inject
    private ThemeVM themeVM;
    @Inject
    private StateModel stateModel;

    public void stageShowed() {
        log.trace("stageShowed");
        grammarAnswerVM.initialize();
        shortAnswerVM.initialize();
        longAnswerVM.initialize();
        gcpAnswerVM.initialize();
        historyVM.displayCurrentInteraction();
        themeVM.initialize();
        themeVM.setLabel();
    }

    public void themeWasChosen() {
        log.trace("themeWasChosen");
        if (stateModel.isHistoryFilteringEnabled()) {
            stateModel.chooseFirstInteractionAsCurrent();
            historyVM.displayCurrentInteraction();
            questionVM.displayCurrentInteraction();
            grammarAnswerVM.displayCurrentAnswer();
            shortAnswerVM.displayCurrentAnswer();
            longAnswerVM.displayCurrentAnswer();
            gcpAnswerVM.displayCurrentAnswer();
        }
    }

    public void answerUpdated(AnswerType answerType) {
        log.trace("answerUpdated");
        switch (answerType) {
            case GRAMMAR -> grammarAnswerVM.displayCurrentAnswer();
            case SHORT -> shortAnswerVM.displayCurrentAnswer();
            case LONG -> longAnswerVM.displayCurrentAnswer();
            case GCP -> gcpAnswerVM.displayCurrentAnswer();
        }
        historyVM.displayCurrentInteraction();
    }

    public void interactionCreated() {
        log.trace("interactionCreated");
        themeVM.updateComboBoxItems();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
    }

    public void isThemeFilterHistoryChanged() {
        log.trace("isThemeFilterHistoryChanged");
        if (stateModel.isHistoryFilteringEnabled()) {
            stateModel.chooseFirstInteractionAsCurrent();
        }
        historyVM.displayCurrentInteraction();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
    }

    public void displayCurrentInteraction() {
        log.trace("displayCurrentInteraction");
        historyVM.displayCurrentInteraction();
        questionVM.displayCurrentInteraction();
        themeVM.updateComboBoxItems();
        themeVM.updateComboBoxCurrentValue();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
    }

    public void addShortcuts(ObservableMap<KeyCombination, Runnable> accelerators) {
        log.trace("addShortcuts");
        accelerators.put(new KeyCodeCombination(UP, CONTROL_DOWN, ALT_DOWN), this::selectPreviousHistoryItem);
        accelerators.put(new KeyCodeCombination(DOWN, CONTROL_DOWN, ALT_DOWN), this::selectNextHistoryItem);
        accelerators.put(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> questionVM.pasteQuestionFromClipboard());
        accelerators.put(new KeyCodeCombination(ESCAPE), () -> questionVM.focusOnQuestionAndSelect());
        accelerators.put(new KeyCodeCombination(ENTER, CONTROL_DOWN), () -> questionVM.onSendQuestionClick());
    }

    void selectPreviousHistoryItem() {
        historyVM.selectPreviousItem();
    }

    void selectNextHistoryItem() {
        historyVM.selectNextItem();
    }
}
