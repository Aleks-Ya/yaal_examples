package gptui.model.question.question;

import gptui.Mdc;
import gptui.model.event.EventModel;
import gptui.model.event.EventSource;
import gptui.model.question.QuestionModel;
import gptui.model.question.gcp.GcpApi;
import gptui.model.question.openai.GptApi;
import gptui.model.state.StateModel;
import gptui.model.storage.Answer;
import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;
import gptui.model.storage.StorageModel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

import static gptui.model.storage.AnswerState.FAIL;
import static gptui.model.storage.AnswerState.NEW;
import static gptui.model.storage.AnswerState.SENT;
import static gptui.model.storage.AnswerState.SUCCESS;
import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static java.util.concurrent.CompletableFuture.runAsync;

@Singleton
class QuestionModelImpl implements QuestionModel, EventSource {
    private static final Logger log = LoggerFactory.getLogger(QuestionModelImpl.class);
    @Inject
    private StateModel stateModel;
    @Inject
    private EventModel eventModel;
    @Inject
    private StorageModel storage;
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
    public synchronized void sendQuestion(InteractionType interactionType) {
        var theme = stateModel.getCurrentTheme();
        var question = stateModel.getEditedQuestion();
        var interactionId = storage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: interactionType=\"{}\", theme=\"{}\", question=\"{}\"", interactionType, theme, question);
            var interaction = new Interaction(interactionId, interactionType, theme, question, Map.of(
                    GRAMMAR, new Answer(GRAMMAR, "", stateModel.getTemperature(GRAMMAR), "", "", NEW),
                    SHORT, new Answer(SHORT, "", stateModel.getTemperature(SHORT), "", "", NEW),
                    LONG, new Answer(LONG, "", stateModel.getTemperature(LONG), "", "", NEW),
                    GCP, new Answer(GCP, "", stateModel.getTemperature(GCP), "", "", NEW)
            ));
            storage.saveInteraction(interaction);
            stateModel.setCurrentInteractionId(interactionId);
            Platform.runLater(() -> eventModel.fire().interactionCreated());
            requestAnswer(interactionId, GCP);
            requestAnswer(interactionId, LONG);
            requestAnswer(interactionId, SHORT);
            requestAnswer(interactionId, GRAMMAR);
        });
    }

    @Override
    public void requestAnswer(InteractionId interactionId, AnswerType answerType) {
        log.info("Sending request for {}...", answerType);
        var interaction = storage.readInteraction(interactionId).orElseThrow();
        var promptOpt = promptFactory.getPrompt(interaction.type(), interaction.theme(), interaction.question(), answerType);
        if (promptOpt.isPresent()) {
            var prompt = promptOpt.get();
            var temperature = stateModel.getTemperature(answerType);
            updateAnswer(interactionId, answerType, answer -> answer.withPrompt(prompt).withState(SENT).withTemperature(temperature));
            runAsync(() -> Mdc.run(interactionId, () -> {
                log.trace("requestAnswer async");
                var answerMd = answerType != GCP ? gptApi.send(prompt, temperature) : gcpApi.send(prompt, temperature);
                var answerHtml = formatConverter.markdownToHtml(answerMd);
                updateAnswer(interactionId, answerType, answer ->
                        answer.withAnswerMd(answerMd).withAnswerHtml(answerHtml).withState(SUCCESS));
                soundService.beenOnAnswer(answerType);
                log.info("The short answer request finished.");
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
        } else {
            log.info("The short answer was skipped.");
        }
    }

    private synchronized void updateAnswer(InteractionId interactionId, AnswerType answerType, Function<Answer, Answer> update) {
        log.trace("updateAnswer: interactionId={}. answerType={}", interactionId, answerType);
        storage.updateInteraction(interactionId, interaction ->
                interaction.withAnswer(update.apply(interaction.getAnswer(answerType).orElseThrow())));
        Platform.runLater(() -> eventModel.fire().answerUpdated());
    }

    @Override
    public String getName() {
        return QuestionModel.class.getSimpleName();
    }
}