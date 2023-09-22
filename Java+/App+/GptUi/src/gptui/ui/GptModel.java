package gptui.ui;

import gptui.Mdc;
import gptui.format.FormatConverter;
import gptui.format.PromptFactory;
import gptui.format.ThemeHelper;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.ui.view.GptViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SENT;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static java.util.concurrent.CompletableFuture.runAsync;

public class GptModel {
    private static final Logger log = LoggerFactory.getLogger(GptModel.class);
    private final GptApi gptApi = new GptApi();
    private final FormatConverter formatConverter = new FormatConverter();
    private final ThemeHelper themeHelper = new ThemeHelper();
    private final GptViewModel viewModel;
    private final GptStorage gptStorage = new GptStorage();
    private final SoundService soundService = new SoundService();
    private final PromptFactory promptFactory = new PromptFactory();

    public GptModel(GptViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.themeListUpdated(readThemeList());
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), null);
    }

    public void sendQuestion(String theme, String question) {
        var interactionId = gptStorage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
            viewModel.setAnswer(QUESTION_CORRECTNESS, "");
            viewModel.setAnswer(SHORT, "");
            viewModel.setAnswer(LONG, "");
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
            viewModel.setAnswer(answerType, answerHtml);
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
                    viewModel.setAnswer(answerType, message);
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
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), currentInteraction);
        viewModel.themeListUpdated(readThemeList());
        viewModel.setAnswerStatusCircleColor(currentInteraction);
    }

    private List<String> readThemeList() {
        return themeHelper.interactionsToThemeList(gptStorage.readAllInteractions());
    }
}
