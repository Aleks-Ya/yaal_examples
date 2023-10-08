package gptui.ui.controller;

import gptui.Mdc;
import gptui.format.ClipboardHelper;
import gptui.format.FormatConverter;
import gptui.format.PromptFactory;
import gptui.format.ThemeHelper;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Function;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerState.SENT;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static java.util.concurrent.CompletableFuture.runAsync;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

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
    @Inject
    private ClipboardHelper clipboardHelper;
    @FXML
    private TextArea questionTextArea;

    @Override
    public void initializeChild() {
        questionTextArea.addEventFilter(KEY_PRESSED, event -> {
            if (event.getCode() == ENTER) {
                log.debug("Send question by Enter");
                event.consume();
                sendQuestion();
            }
        });
    }

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        log.debug("Send question by Send button");
        sendQuestion();
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        model.setQuestion(questionTextArea.getText());
    }

    private void sendQuestion() {
        var theme = model.getEditedTheme();
        var question = model.getEditedQuestion();
        var interactionId = gptStorage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
            updateInteraction(interactionId, interaction -> interaction
                    .withTheme(theme)
                    .withQuestion(question)
                    .withAnswer(QUESTION_CORRECTNESS, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW))
                    .withAnswer(SHORT, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW))
                    .withAnswer(LONG, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW)));
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
        model.fireModelChanged(this);
    }

    @Override
    public void interactionChosenFromHistory(Model model, EventSource source) {
        Optional.ofNullable(model.getCurrentInteraction())
                .map(Interaction::question)
                .filter(question -> !question.equals(questionTextArea.getText()))
                .ifPresent(question -> questionTextArea.setText(question));
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        model.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            var question = clipboardHelper.getTextFromClipboard();
            questionTextArea.setText(question);
            questionTextArea.requestFocus();
            questionTextArea.positionCaret(questionTextArea.getText().length());
            model.setQuestion(question);
        });
    }
}

