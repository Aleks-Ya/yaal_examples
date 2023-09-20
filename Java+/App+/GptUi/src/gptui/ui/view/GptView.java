package gptui.ui.view;

import javafx.application.Platform;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import static javafx.geometry.Orientation.VERTICAL;

public class GptView extends VBox {
    private final GptViewModel viewModel = new GptViewModel();
    private final InteractionHistoryPane interactionHistoryPane = new InteractionHistoryPane();
    private final ThemePane themePane = new ThemePane();
    private final QuestionPane questionPane = new QuestionPane(this::sendQuestion);
    private final QuestionCorrectnessPane questionCorrectnessPane = new QuestionCorrectnessPane();
    private final AnswerPane shortAnswerPane = new AnswerPane("Short\nanswer:");
    private final AnswerPane longAnswerPane = new AnswerPane("Long\nanswer:");

    public GptView() {
        createView();
        bindViewModel();
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
