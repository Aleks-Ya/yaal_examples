package gptui.viewmodel;

import gptui.model.state.StateModel;
import gptui.model.storage.AnswerType;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        grammarAnswerVM.displayInitialState();
        shortAnswerVM.displayInitialState();
        longAnswerVM.displayInitialState();
        gcpAnswerVM.displayInitialState();
        historyVM.displayCurrentInteraction();
        historyVM.addShortcuts();
        questionVM.addShortcuts();
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
        stateModel.chooseFirstInteractionAsCurrent();
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

    public void interactionDeleted() {
        log.trace("interactionDeleted");
        themeVM.updateComboBoxItems();
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

    public void currentInteractionChosen() {
        log.trace("currentInteractionChosen");
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();

        historyVM.displayCurrentInteraction();
        questionVM.displayCurrentInteraction();
        themeVM.updateComboBoxItems();
        themeVM.updateComboBoxCurrentValue();
    }
}
