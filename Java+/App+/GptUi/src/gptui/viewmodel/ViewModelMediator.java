package gptui.viewmodel;

import gptui.model.storage.AnswerType;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
class ViewModelMediator {
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

    public void stageShowed() {
        grammarAnswerVM.displayInitialState();
        shortAnswerVM.displayInitialState();
        longAnswerVM.displayInitialState();
        gcpAnswerVM.displayInitialState();
        historyVM.setLabel();
        historyVM.addShortcuts();
        questionVM.addShortcuts();
        themeVM.setLabel();
    }

    public void themeWasChosen() {
        historyVM.displayCurrentHistoryIfHistoryFiltered();
        questionVM.displayCurrentQuestionIfChangedAndHistoryFiltered();
    }

    public void answerUpdated(AnswerType answerType) {
//        switch (answerType) {
//            case GRAMMAR -> grammarAnswerVM.displayCurrentAnswer();
//            case SHORT -> shortAnswerVM.displayCurrentAnswer();
//            case LONG -> longAnswerVM.displayCurrentAnswer();
//            case GCP -> gcpAnswerVM.displayCurrentAnswer();
//        }
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();
        historyVM.displayCurrentHistory();
    }

    public void interactionCreated() {
        themeVM.updateComboBoxItems();
    }

    public void interactionDeleted() {
        themeVM.updateComboBoxItems();
    }

    public void isThemeFilterHistoryChanged() {
        historyVM.displayCurrentHistory();
    }

    public void currentInteractionChosen() {
        grammarAnswerVM.displayCurrentAnswer();
        shortAnswerVM.displayCurrentAnswer();
        longAnswerVM.displayCurrentAnswer();
        gcpAnswerVM.displayCurrentAnswer();

        historyVM.interactionChosenFromHistory();
        questionVM.displayCurrentQuestionIfChanged();
        themeVM.updateComboBoxItems();
        themeVM.updateComboBoxCurrentValue();
    }
}
