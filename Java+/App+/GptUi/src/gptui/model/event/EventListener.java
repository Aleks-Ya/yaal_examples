package gptui.model.event;

public interface EventListener {
    default void interactionChosenFromHistory() {
    }

    default void stageWasShowed() {
    }

    default void themeWasChosen() {
    }

    default void isThemeFilterHistoryChanged() {
    }

    default void interactionCreated() {
    }

    default void interactionsUpdated() {
    }

    default void interactionDeleted() {
    }

    default void answerUpdated() {
    }

}
