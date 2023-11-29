package gptui.gpt.question;

import gptui.Mdc;
import gptui.gpt.QuestionApi;
import gptui.gpt.Temperatures;
import gptui.gpt.gcp.GcpApi;
import gptui.gpt.openai.GptApi;
import gptui.storage.Answer;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionType;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Function;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerState.SENT;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static java.util.concurrent.CompletableFuture.runAsync;

@Singleton
class QuestionApiImpl implements QuestionApi, EventSource {
    private static final Logger log = LoggerFactory.getLogger(QuestionApiImpl.class);
    @Inject
    private Model model;
    @Inject
    private GptStorage storage;
    @Inject
    private PromptFactory promptFactory;
    @Inject
    private GptApi gptApi;
    @Inject
    private GcpApi gcpApi;
    @Inject
    private SoundService soundService;
    @Inject
    private FormatConverter formatConverter;

    @Override
    public void sendQuestion(InteractionType interactionType, Temperatures temperatures) {
        var theme = model.getEditedTheme();
        var question = model.getEditedQuestion();
        var interactionId = storage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: interactionType=\"{}\", theme=\"{}\", question=\"{}\"", interactionType, theme, question);
            updateInteraction(interactionId, interaction -> interaction
                    .withTheme(theme)
                    .withQuestion(question)
                    .withType(interactionType)
                    .withAnswer(GRAMMAR, answer -> answer
                            .withPrompt("")
                            .withTemperature(temperatures.getTemperature(GRAMMAR))
                            .withAnswerMd("")
                            .withAnswerHtml("")
                            .withState(NEW))
                    .withAnswer(SHORT, answer -> answer
                            .withPrompt("")
                            .withTemperature(temperatures.getTemperature(SHORT))
                            .withAnswerMd("")
                            .withAnswerHtml("")
                            .withState(NEW))
                    .withAnswer(LONG, answer -> answer
                            .withPrompt("")
                            .withTemperature(temperatures.getTemperature(LONG))
                            .withAnswerMd("")
                            .withAnswerHtml("")
                            .withState(NEW))
                    .withAnswer(GCP, answer -> answer
                            .withPrompt("")
                            .withTemperature(temperatures.getTemperature(GCP))
                            .withAnswerMd("")
                            .withAnswerHtml("")
                            .withState(NEW)));
            requestAnswer(interactionId, GCP);
            requestAnswer(interactionId, LONG);
            requestAnswer(interactionId, SHORT);
            requestAnswer(interactionId, GRAMMAR);
        });
    }

    @Override
    public void requestAnswer(InteractionId interactionId, AnswerType answerType) {
        log.info("Sending request for {}...", answerType);
        runAsync(() -> Mdc.run(interactionId, () -> {
            log.trace("requestAnswer async");
            var interaction = storage.readInteraction(interactionId).orElseThrow();
            var promptOpt = promptFactory.getPrompt(interaction.type(), interaction.theme(), interaction.question(), answerType);
            if (promptOpt.isPresent()) {
                var prompt = promptOpt.get();
                updateAnswer(interactionId, answerType, answer -> answer.withPrompt(prompt).withState(SENT));
                var temperature = storage.readInteraction(interactionId)
                        .orElseThrow().getAnswer(answerType)
                        .orElseThrow().temperature();
                var answerMd = answerType != GCP ? gptApi.send(prompt, temperature) : gcpApi.send(prompt, temperature);
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
