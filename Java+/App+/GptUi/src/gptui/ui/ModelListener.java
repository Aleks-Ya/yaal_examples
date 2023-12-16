package gptui.ui;

public interface ModelListener {
    default void interactionChosenFromHistory(Model model) {
    }

    default void stageWasShowed(Model model) {
    }

    default void themeWasChosen(Model model) {
    }

    default void isThemeFilterHistoryChanged(Model model) {
    }

    default void interactionsUpdated(Model model) {
    }

    default void answerUpdated(Model model) {
    }

}
