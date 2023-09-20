package gptui.ui;

import gptui.Mdc;
import gptui.format.FormatConverter;
import gptui.format.PromptFactory;
import gptui.format.ThemeHelper;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.Answer;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.ui.view.GptViewModel;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

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
            requestLongAnswer(theme, question, interactionId);
            requestShortAnswer(theme, question, interactionId);
            requestQuestionCorrectness(question, interactionId);
        });
    }

    private void requestQuestionCorrectness(String question, InteractionId interactionId) {
        log.info("Sending request for a question correctness...");
        var prompt = promptFactory.getPrompt(null, question, QUESTION_CORRECTNESS);
        runAsync(() -> Mdc.run(interactionId, () -> {
            var answerMd = gptApi.send(prompt);
            var answerHtml = formatConverter.markdownToHtml(answerMd);
            viewModel.setAnswer(QUESTION_CORRECTNESS, answerHtml);
            updateInteraction(interactionId, interaction -> interaction
                    .withAnswer(new Answer(QUESTION_CORRECTNESS, prompt, answerMd, answerHtml)));
            soundService.beenOnAnswer(QUESTION_CORRECTNESS);
            log.info("The question correctness answer request finished.");
        }));
    }

    private void requestShortAnswer(String theme, String question, InteractionId interactionId) {
        log.info("Sending request for a shot answer...");
        var prompt = promptFactory.getPrompt(theme, question, SHORT);
        viewModel.setAnswerStatusCircleColor(SHORT, Color.BLUE);
        runAsync(() -> Mdc.run(interactionId, () -> {
            var answerMd = gptApi.send(prompt);
            var answerHtml = formatConverter.markdownToHtml(answerMd);
            viewModel.setAnswer(SHORT, answerHtml);
            updateInteraction(interactionId, interaction -> interaction
                    .withAnswer(new Answer(SHORT, prompt, answerMd, answerHtml)));
            soundService.beenOnAnswer(SHORT);
            viewModel.setAnswerStatusCircleColor(SHORT, Color.GREEN);
            log.info("The short answer request finished.");
        })).handle((res, e) -> {
            if (e != null) {
                Mdc.run(interactionId, () -> viewModel.setAnswerStatusCircleColor(SHORT, Color.RED));
                return e;
            } else {
                return res;
            }
        });
    }

    private void requestLongAnswer(String theme, String question, InteractionId interactionId) {
        log.info("Sending request for a long answer...");
        var prompt = promptFactory.getPrompt(theme, question, LONG);
        viewModel.setAnswerStatusCircleColor(LONG, Color.BLUE);
        runAsync(() -> Mdc.run(interactionId, () -> {
            var answerMd = gptApi.send(prompt);
            var answerHtml = formatConverter.markdownToHtml(answerMd);
            viewModel.setAnswer(LONG, answerHtml);
            updateInteraction(interactionId, interaction -> interaction
                    .withAnswer(new Answer(LONG, prompt, answerMd, answerHtml)));
            soundService.beenOnAnswer(LONG);
            viewModel.setAnswerStatusCircleColor(LONG, Color.GREEN);
            log.info("The long answer request finished.");
        })).handle((res, e) -> {
            if (e != null) {
                Mdc.run(interactionId, () -> viewModel.setAnswerStatusCircleColor(LONG, Color.RED));
                return e;
            } else {
                return res;
            }
        });
    }

    private void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        gptStorage.updateInteraction(interactionId, update);
        var currentInteraction = gptStorage.readInteraction(interactionId).orElseThrow();
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), currentInteraction);
        viewModel.themeListUpdated(readThemeList());
    }

    private List<String> readThemeList() {
        return themeHelper.interactionsToThemeList(gptStorage.readAllInteractions());
    }
}
