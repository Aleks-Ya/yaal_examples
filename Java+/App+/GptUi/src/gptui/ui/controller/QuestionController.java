package gptui.ui.controller;

import gptui.Mdc;
import gptui.format.ClipboardHelper;
import gptui.format.FormatConverter;
import gptui.format.PromptFactory;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.Answer;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
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
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.QUESTION;
import static java.util.concurrent.CompletableFuture.runAsync;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.input.KeyCombination.ALT_DOWN;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class QuestionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Inject
    private GptStorage storage;
    @Inject
    private PromptFactory promptFactory;
    @Inject
    private GptApi gptApi;
    @Inject
    private SoundService soundService;
    @Inject
    private FormatConverter formatConverter;
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
                sendQuestion(QUESTION);
            }
        });
    }

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        log.debug("Send question by Send button");
        sendQuestion(QUESTION);
    }

    @FXML
    void sendDefinition(ActionEvent ignoredEvent) {
        log.debug("Send definition by Send button");
        sendQuestion(DEFINITION);
    }

    @FXML
    void sendGrammar(ActionEvent ignoredEvent) {
        log.debug("Send grammar by Send button");
        sendQuestion(InteractionType.GRAMMAR);
    }

    @FXML
    public void sendFact(ActionEvent ignoredEvent) {
        log.debug("Send fact by Send button");
        sendQuestion(FACT);
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        var interactionId = model.getCurrentInteractionId();
        Mdc.run(interactionId, () -> {
            var interaction = storage.readInteraction(interactionId).orElseThrow();
            var theme = interaction.theme();
            var question = interaction.question();
            var interactionType = interaction.type();
            log.info("Regenerate question: interactionId=\"{}\", interactionType=\"{}\", theme=\"{}\", question=\"{}\"",
                    interactionId, interactionType, theme, question);
            requestAnswer(theme, question, interactionId, interactionType, LONG);
            requestAnswer(theme, question, interactionId, interactionType, SHORT);
            requestAnswer(theme, question, interactionId, interactionType, GRAMMAR);
        });
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        model.setEditedQuestion(questionTextArea.getText());
    }

    private void sendQuestion(InteractionType interactionType) {
        var theme = model.getEditedTheme();
        var question = model.getEditedQuestion();
        var interactionId = storage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: interactionType=\"{}\", theme=\"{}\", question=\"{}\"", interactionType, theme, question);
            updateInteraction(interactionId, interaction -> interaction
                    .withTheme(theme)
                    .withQuestion(question)
                    .withType(interactionType)
                    .withAnswer(GRAMMAR, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW))
                    .withAnswer(SHORT, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW))
                    .withAnswer(LONG, answer -> answer.withPrompt("").withAnswerMd("").withAnswerHtml("").withState(NEW)));
            requestAnswer(theme, question, interactionId, interactionType, LONG);
            requestAnswer(theme, question, interactionId, interactionType, SHORT);
            requestAnswer(theme, question, interactionId, interactionType, GRAMMAR);
        });
    }

    private void requestAnswer(String theme, String question, InteractionId interactionId, InteractionType interactionType,
                               AnswerType answerType) {
        log.info("Sending request for {}...", answerType);
        runAsync(() -> Mdc.run(interactionId, () -> {
            log.trace("requestAnswer async");
            var promptOpt = promptFactory.getPrompt(interactionType, theme, question, answerType);
            if (promptOpt.isPresent()) {
                var prompt = promptOpt.get();
                updateAnswer(interactionId, answerType, answer -> answer.withPrompt(prompt).withState(SENT));
                var answerMd = gptApi.send(prompt);
                var answerHtml = formatConverter.markdownToHtml(answerMd);
                updateAnswer(interactionId, answerType, answer ->
                        answer.withAnswerMd(answerMd).withAnswerHtml(answerHtml).withState(SUCCESS));
                soundService.beenOnAnswer(answerType);
                log.info("The short answer request finished.");
            } else {
                log.info("The short answer was skipped.");
            }
        })).handle((res, e) -> {
            if (e != null) {
                log.error("Sending question exception", e);
                Mdc.run(interactionId, () -> {
                    var message = e.getCause().getMessage();
                    updateAnswer(interactionId, answerType, answer ->
                            answer.withAnswerMd(message).withAnswerHtml(message).withState(FAIL));
                    soundService.beenOnAnswer(answerType);
                });
                return e;
            } else {
                return res;
            }
        });
    }

    @Override
    public void interactionChosenFromHistory(Model model, EventSource source) {
        log.trace("interactionChosenFromHistory");
        Optional.ofNullable(model.getCurrentInteractionId())
                .map(storage::readInteraction)
                .map(Optional::orElseThrow)
                .map(Interaction::question)
                .filter(question -> !question.equals(questionTextArea.getText()))
                .ifPresent(question -> {
                    questionTextArea.setText(question);
                    model.setEditedQuestion(question);
                });
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        log.trace("stageWasShowed");
        model.addAccelerator(new KeyCodeCombination(V, CONTROL_DOWN, ALT_DOWN), () -> {
            log.debug("pasteQuestionFromClipboardAndFocus");
            var question = clipboardHelper.getTextFromClipboard();
            questionTextArea.setText(question);
            questionTextArea.requestFocus();
            questionTextArea.positionCaret(questionTextArea.getText().length());
            model.setEditedQuestion(question);
        });
        model.addAccelerator(new KeyCodeCombination(Q, CONTROL_DOWN), () -> sendQuestion(QUESTION));
        model.addAccelerator(new KeyCodeCombination(D, CONTROL_DOWN), () -> sendQuestion(DEFINITION));
        model.addAccelerator(new KeyCodeCombination(G, CONTROL_DOWN), () -> sendQuestion(InteractionType.GRAMMAR));
        model.addAccelerator(new KeyCodeCombination(F, CONTROL_DOWN), () -> sendQuestion(FACT));
    }

    private synchronized void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        log.trace("updateInteraction {}", interactionId);
        storage.updateInteraction(interactionId, update);
        var history = storage.readAllInteractions();
        model.setHistory(history);
        model.setCurrentInteractionId(interactionId);
        Platform.runLater(() -> model.fireModelChanged(this));
    }

    private synchronized void updateAnswer(InteractionId interactionId, AnswerType answerType, Function<Answer, Answer> update) {
        log.trace("updateAnswer: interactionId={}. answerType={}", interactionId, answerType);
        storage.updateInteraction(interactionId, interaction ->
                interaction.withAnswer(update.apply(interaction.getAnswer(answerType).orElseThrow())));
        Platform.runLater(() -> model.fireModelChanged(this));
    }
}

