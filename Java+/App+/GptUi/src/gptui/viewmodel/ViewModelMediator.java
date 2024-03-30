package gptui.viewmodel;

import gptui.model.clipboard.ClipboardModel;
import gptui.model.file.FileModel;
import gptui.model.question.QuestionModel;
import gptui.model.state.StateModel;
import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;
import gptui.model.storage.Theme;
import gptui.model.storage.ThemeId;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

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
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;
    @Inject
    private FileModel fileModel;

    void stageShowed() {
        log.trace("stageShowed");
        grammarAnswerVM.initialize();
        shortAnswerVM.initialize();
        longAnswerVM.initialize();
        gcpAnswerVM.initialize();
        historyVM.displayCurrentInteraction();
        themeVM.initialize();
        themeVM.setLabel();
    }

    void themeWasChosen() {
        log.trace("themeWasChosen");
        if (stateModel.isHistoryFilteringEnabled()) {
            stateModel.chooseFirstInteractionAsCurrent();
        }
        themeVM.updateComboBoxItems();
        themeVM.updateComboBoxSelectedItemFromStateModel();
        historyVM.displayCurrentInteraction();
        questionVM.displayCurrentInteraction();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
    }

    private void answerUpdated(AnswerType answerType) {
        log.trace("answerUpdated");
        switch (answerType) {
            case GRAMMAR -> grammarAnswerVM.displayCurrentAnswer();
            case SHORT -> shortAnswerVM.displayCurrentAnswer();
            case LONG -> longAnswerVM.displayCurrentAnswer();
            case GCP -> gcpAnswerVM.displayCurrentAnswer();
        }
        historyVM.displayCurrentInteraction();
    }

    void isThemeFilterHistoryChanged() {
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

    void displayCurrentInteraction() {
        log.trace("displayCurrentInteraction");
        historyVM.displayCurrentInteraction();
        questionVM.displayCurrentInteraction();
        themeVM.updateComboBoxItems();
        themeVM.updateComboBoxSelectedItemFromCurrentInteraction();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
    }

    void addShortcuts(ObservableMap<KeyCombination, Runnable> accelerators) {
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

    InteractionId getCurrentInteractionId() {
        return stateModel.getCurrentInteractionId();
    }

    Interaction getCurrentInteraction() {
        return getCurrentInteractionOpt().orElseThrow();
    }

    Optional<Interaction> getCurrentInteractionOpt() {
        return stateModel.getCurrentInteractionOpt();
    }

    void setCurrentInteractionId(InteractionId currentInteractionId) {
        stateModel.setCurrentInteractionId(currentInteractionId);
    }

    void deleteCurrentInteraction() {
        stateModel.deleteCurrentInteraction();
    }

    List<Interaction> getFullHistory() {
        return stateModel.getFullHistory();
    }

    List<Interaction> getFilteredHistory() {
        return stateModel.getFilteredHistory();
    }

    Theme getCurrentTheme() {
        return stateModel.getCurrentTheme();
    }

    Theme getTheme(ThemeId themeId) {
        return stateModel.getTheme(themeId);
    }

    Boolean isHistoryFilteringEnabled() {
        return stateModel.isHistoryFilteringEnabled();
    }

    void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled) {
        stateModel.setIsHistoryFilteringEnabled(isHistoryFilteringEnabled);
    }

    void setCurrentTheme(Theme currentTheme) {
        stateModel.setCurrentTheme(currentTheme);
    }

    List<Theme> getThemes() {
        return stateModel.getThemes();
    }

    Theme addTheme(String theme) {
        return stateModel.addTheme(theme);
    }

    Long getInteractionCountInTheme(String theme) {
        return stateModel.getInteractionCountInTheme(theme);
    }

    void setEditedQuestion(String question) {
        stateModel.setEditedQuestion(question);
    }

    Boolean isEnteringNewQuestion() {
        return stateModel.isEnteringNewQuestion();
    }

    void requestAnswer(InteractionId interactionId, AnswerType answerType) {
        questionModel.requestAnswer(interactionId, answerType, () -> answerUpdated(answerType));
    }

    InteractionId createInteraction(InteractionType interactionType) {
        var interaction = stateModel.createInteraction(interactionType);
        themeVM.updateComboBoxItems();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
        return interaction;
    }

    String getTextFromClipboard() {
        return clipboardModel.getTextFromClipboard();
    }

    void putHtmlToClipboard(String html) {
        clipboardModel.putHtmlToClipboard(html);
    }

    Integer getTemperature(AnswerType answerType) {
        return stateModel.getTemperature(answerType);
    }

    void setTemperature(AnswerType answerType, Integer temperature) {
        stateModel.setTemperature(answerType, temperature);
    }

    void chooseFirstInteractionAsCurrent() {
        stateModel.chooseFirstInteractionAsCurrent();
    }

    void chooseFirstThemeAsCurrent() {
        stateModel.setFirstThemeAsCurrent();
    }

    InputStream getAppIcon() {
        return fileModel.getAppIcon();
    }

    String getAppVersion() {
        return fileModel.getAppVersion();
    }

    URL getFxmlLocation() {
        return fileModel.getFxmlLocation();
    }
}
