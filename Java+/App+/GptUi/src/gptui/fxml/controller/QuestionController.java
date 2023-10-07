package gptui.fxml.controller;

import gptui.Mdc;
import gptui.format.FormatConverter;
import gptui.format.PromptFactory;
import gptui.format.ThemeHelper;
import gptui.fxml.Model;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Function;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SENT;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static java.util.concurrent.CompletableFuture.runAsync;

class QuestionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Inject
    private GptStorage gptStorage;
    @Inject
    private PromptFactory promptFactory;
    @Inject
    private GptApi gptApi;
    @Inject
    private SoundService soundService;
    @Inject
    private FormatConverter formatConverter;
    @Inject
    private ThemeHelper themeHelper;
    @FXML
    private TextArea questionTextArea;

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        var theme = model.getCurrentTheme();
        var question = model.getQuestion();
        sendQuestion(theme, question);
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        model.setQuestion(questionTextArea.getText());
    }

    private void sendQuestion(String theme, String question) {
        var interactionId = gptStorage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
            updateInteraction(interactionId, interaction -> interaction
                    .withTheme(theme)
                    .withQuestion(question));
            requestAnswer(theme, question, interactionId, LONG);
            requestAnswer(theme, question, interactionId, SHORT);
            requestAnswer(theme, question, interactionId, QUESTION_CORRECTNESS);
        });
    }

    private void requestAnswer(String theme, String question, InteractionId interactionId, AnswerType answerType) {
        log.info("Sending request for {}...", answerType);
        runAsync(() -> Mdc.run(interactionId, () -> {
            var prompt = promptFactory.getPrompt(theme, question, answerType);
            updateInteraction(interactionId, interaction ->
                    interaction.withAnswer(answerType, answer -> answer.withPrompt(prompt).withState(SENT)));
            var answerMd = gptApi.send(prompt);
            var answerHtml = formatConverter.markdownToHtml(answerMd);
            updateInteraction(interactionId, interaction -> interaction.withAnswer(answerType, answer ->
                    answer.withAnswerMd(answerMd).withAnswerHtml(answerHtml).withState(SUCCESS)));
            soundService.beenOnAnswer(answerType);
            log.info("The short answer request finished.");
        })).handle((res, e) -> {
            if (e != null) {
                Mdc.run(interactionId, () -> {
                    var message = e.getCause().getMessage();
                    updateInteraction(interactionId, interaction -> interaction.withAnswer(answerType,
                            answer -> answer.withAnswerMd(message).withAnswerHtml(message).withState(FAIL)));
                    soundService.beenOnAnswer(answerType);
                });
                return e;
            } else {
                return res;
            }
        });
    }

    private synchronized void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        gptStorage.updateInteraction(interactionId, update);
        var currentInteraction = gptStorage.readInteraction(interactionId).orElseThrow();
        var interactionHistory = gptStorage.readAllInteractions();
        model.setInteractionHistory(interactionHistory);
        model.setCurrentInteraction(currentInteraction);
        model.setThemeList(themeHelper.interactionsToThemeList(interactionHistory));
        model.fireModelChanged();
    }

    @Override
    public void interactionChosenFromHistory(Model model) {
        Optional.ofNullable(model.getCurrentInteraction())
                .map(Interaction::question)
                .filter(question -> !question.equals(questionTextArea.getText()))
                .ifPresent(question -> questionTextArea.setText(question));
    }
}

