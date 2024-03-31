package gptui.viewmodel.mediator;

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
import gptui.viewmodel.answer.AnswerVmMediator;
import gptui.viewmodel.answer.AnswerVmModule;
import gptui.viewmodel.history.HistoryVmMediator;
import gptui.viewmodel.question.QuestionVmMediator;
import gptui.viewmodel.theme.ThemeVmMediator;
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

import static gptui.model.storage.InteractionType.QUESTION;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

@Singleton
class ViewModelMediatorImpl implements HistoryMediator, QuestionMediator, ThemeMediator, AnswerMediator,
        GptUiMediator, GptUiApplicationMediator {
    private static final Logger log = LoggerFactory.getLogger(ViewModelMediatorImpl.class);
    @Inject
    @Named(AnswerVmModule.GRAMMAR)
    private AnswerVmMediator grammarAnswerVM;
    @Inject
    @Named(AnswerVmModule.SHORT)
    private AnswerVmMediator shortAnswerVM;
    @Inject
    @Named(AnswerVmModule.LONG)
    private AnswerVmMediator longAnswerVM;
    @Inject
    @Named(AnswerVmModule.GCP)
    private AnswerVmMediator gcpAnswerVM;
    @Inject
    private HistoryVmMediator historyVM;
    @Inject
    private QuestionVmMediator questionVM;
    @Inject
    private ThemeVmMediator themeVM;
    @Inject
    private StateModel stateModel;
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;
    @Inject
    private FileModel fileModel;

    @Override
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

    @Override
    public void themeWasChosen() {
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
        questionVM.focusOnQuestionAndSelect();
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

    @Override
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

    @Override
    public void displayCurrentInteraction() {
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

    @Override
    public void addShortcuts(ObservableMap<KeyCombination, Runnable> accelerators) {
        log.trace("addShortcuts");
        accelerators.put(new KeyCodeCombination(UP, CONTROL_DOWN, ALT_DOWN), this::selectPreviousHistoryItem);
        accelerators.put(new KeyCodeCombination(DOWN, CONTROL_DOWN, ALT_DOWN), this::selectNextHistoryItem);
        accelerators.put(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> questionVM.pasteQuestionFromClipboard());
        accelerators.put(new KeyCodeCombination(ESCAPE), () -> questionVM.focusOnQuestionAndSelect());
        accelerators.put(new KeyCodeCombination(ENTER, CONTROL_DOWN), () -> questionVM.createNewInteractionAndRequestAnswers(QUESTION));
    }

    void selectPreviousHistoryItem() {
        historyVM.selectPreviousItem();
    }

    @Override
    public void selectNextHistoryItem() {
        historyVM.selectNextItem();
    }

    @Override
    public InteractionId getCurrentInteractionId() {
        return stateModel.getCurrentInteractionId();
    }

    @Override
    public Interaction getCurrentInteraction() {
        return getCurrentInteractionOpt().orElseThrow();
    }

    @Override
    public Optional<Interaction> getCurrentInteractionOpt() {
        return stateModel.getCurrentInteractionOpt();
    }

    @Override
    public void setCurrentInteractionId(InteractionId currentInteractionId) {
        stateModel.setCurrentInteractionId(currentInteractionId);
    }

    @Override
    public void deleteCurrentInteraction() {
        stateModel.deleteCurrentInteraction();
    }

    @Override
    public List<Interaction> getFullHistory() {
        return stateModel.getFullHistory();
    }

    @Override
    public List<Interaction> getFilteredHistory() {
        return stateModel.getFilteredHistory();
    }

    @Override
    public Theme getCurrentTheme() {
        return stateModel.getCurrentTheme();
    }

    @Override
    public Theme getTheme(ThemeId themeId) {
        return stateModel.getTheme(themeId);
    }

    @Override
    public Boolean isHistoryFilteringEnabled() {
        return stateModel.isHistoryFilteringEnabled();
    }

    @Override
    public void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled) {
        stateModel.setIsHistoryFilteringEnabled(isHistoryFilteringEnabled);
    }

    @Override
    public void setCurrentTheme(Theme currentTheme) {
        stateModel.setCurrentTheme(currentTheme);
    }

    @Override
    public List<Theme> getThemes() {
        return stateModel.getThemes();
    }

    @Override
    public Theme addTheme(String theme) {
        return stateModel.addTheme(theme);
    }

    @Override
    public Long getInteractionCountInTheme(String theme) {
        return stateModel.getInteractionCountInTheme(theme);
    }

    @Override
    public void setEditedQuestion(String question) {
        stateModel.setEditedQuestion(question);
    }

    @Override
    public Boolean isEnteringNewQuestion() {
        return stateModel.isEnteringNewQuestion();
    }

    @Override
    public void requestAnswer(InteractionId interactionId, AnswerType answerType) {
        questionModel.requestAnswer(interactionId, answerType, () -> answerUpdated(answerType));
    }

    @Override
    public InteractionId createInteraction(InteractionType interactionType) {
        var interaction = stateModel.createInteraction(interactionType);
        themeVM.updateComboBoxItems();
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
        return interaction;
    }

    @Override
    public String getTextFromClipboard() {
        return clipboardModel.getTextFromClipboard();
    }

    @Override
    public void putHtmlToClipboard(String html) {
        clipboardModel.putHtmlToClipboard(html);
    }

    @Override
    public Integer getTemperature(AnswerType answerType) {
        return stateModel.getTemperature(answerType);
    }

    @Override
    public void setTemperature(AnswerType answerType, Integer temperature) {
        stateModel.setTemperature(answerType, temperature);
    }

    @Override
    public void chooseFirstInteractionAsCurrent() {
        stateModel.chooseFirstInteractionAsCurrent();
    }

    @Override
    public void chooseFirstThemeAsCurrent() {
        stateModel.setFirstThemeAsCurrent();
    }

    @Override
    public InputStream getAppIcon() {
        return fileModel.getAppIcon();
    }

    @Override
    public String getAppVersion() {
        return fileModel.getAppVersion();
    }

    @Override
    public URL getFxmlLocation() {
        return fileModel.getFxmlLocation();
    }
}
