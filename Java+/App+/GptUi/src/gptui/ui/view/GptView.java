package gptui.ui.view;

import javafx.application.Platform;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

import java.util.Map;

import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static javafx.geometry.Orientation.VERTICAL;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

public class GptView extends VBox {
    private final GptViewModel viewModel;
    private final InteractionHistoryPane interactionHistoryPane = new InteractionHistoryPane();
    private final ThemePane themePane = new ThemePane();
    private final QuestionPane questionPane = new QuestionPane(this::sendQuestion);
    private final QuestionCorrectnessPane questionCorrectnessPane = new QuestionCorrectnessPane();
    private final AnswerPane shortAnswerPane = new AnswerPane("Short\nanswer:");
    private final AnswerPane longAnswerPane = new AnswerPane("Long\nanswer:");

    public GptView(GptViewModel viewModel) {
        this.viewModel = viewModel;
        createView();
        bindViewModel();
    }

    public Map<KeyCombination, Runnable> getAccelerators() {
        return Map.of(
                new KeyCodeCombination(DIGIT1, CONTROL_DOWN), () -> viewModel.copyAnswerToClipboard(SHORT),
                new KeyCodeCombination(DIGIT2, CONTROL_DOWN), () -> viewModel.copyAnswerToClipboard(LONG));
    }

    private void createView() {
        getChildren().addAll(interactionHistoryPane, new Separator(VERTICAL),
                themePane, new Separator(VERTICAL),
                questionPane, new Separator(VERTICAL),
                questionCorrectnessPane, new Separator(VERTICAL),
                shortAnswerPane, new Separator(VERTICAL),
                longAnswerPane);
    }

    private void bindViewModel() {
        interactionHistoryPane.getValueProperty().bindBidirectional(viewModel.interactionHistoryValueProperty());
        interactionHistoryPane.getItemsProperty().bindBidirectional(viewModel.interactionHistoryItemsProperty());
        themePane.getValueProperty().bindBidirectional(viewModel.themeValueProperty());
        themePane.getItemsProperty().bindBidirectional(viewModel.themeItemsProperty());
        themePane.getValueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !themePane.getItems().contains(newValue)) {
                themePane.getItems().add(newValue);
            }
        });
        questionPane.getTextProperty().bindBidirectional(viewModel.questionProperty());
        viewModel.questionCorrectnessProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> questionCorrectnessPane.setContent(newValue)));
        viewModel.shortAnswerProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> shortAnswerPane.setContent(newValue)));
        viewModel.longAnswerProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> longAnswerPane.setContent(newValue)));
        shortAnswerPane.statusCircleFillProperty().bindBidirectional(viewModel.shortAnswerStatusCircleProperty());
        longAnswerPane.statusCircleFillProperty().bindBidirectional(viewModel.longAnswerStatusCircleProperty());
    }

    private void sendQuestion() {
        viewModel.sendQuestion();
    }
}
