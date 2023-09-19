package gptui.ui;

import gptui.Mdc;
import gptui.format.FormatConverter;
import gptui.format.ThemeHelper;
import gptui.gpt.GptApi;
import gptui.media.SoundService;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.ui.view.GptViewModel;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class GptModel {
    private static final Logger log = LoggerFactory.getLogger(GptModel.class);
    private final GptApi gptApi = new GptApi();
    private final FormatConverter formatConverter = new FormatConverter();
    private final ThemeHelper themeHelper = new ThemeHelper();
    private final GptViewModel viewModel;
    private final GptStorage gptStorage = new GptStorage();
    private final SoundService soundService = new SoundService();

    public GptModel(GptViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.themeListUpdated(readThemeList());
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), null);
    }

    public void sendQuestion(String theme, String question) {
        var interactionId = gptStorage.newInteractionId();
        Mdc.run(interactionId, () -> {
            log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
            viewModel.setQuestionCorrectnessAnswer("");
            viewModel.setShortAnswer("");
            viewModel.setLongAnswer("");
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
        var askForQuestionCorrectness = String.format("""
                        I will give you a sentence.
                        Check if the sentence has grammatical mistakes.
                        It is not a mistake if the sentence starts with "How to".
                        The sentence is `%s`.""".stripIndent().replace("\n", " "),
                question);
        supplyAsync(() -> Mdc.call(interactionId, () -> gptApi.send(askForQuestionCorrectness)))
                .thenAccept(questionCorrectnessAnswer -> Mdc.run(interactionId, () -> {
                    viewModel.setQuestionCorrectnessAnswer(questionCorrectnessAnswer);
                    updateInteraction(interactionId, interaction -> interaction
                            .withAskForQuestionCorrectness(askForQuestionCorrectness)
                            .withQuestionCorrectnessAnswer(questionCorrectnessAnswer));
                    soundService.beep1();
                    log.info("The question correctness answer request finished.");
                }));
    }

    private void requestShortAnswer(String theme, String question, InteractionId interactionId) {
        log.info("Sending request for a shot answer...");
        var askForShortAnswer = String.format("""
                        I will ask you a question about "%s".
                        You should answer with a short response.
                        Format your answer into Markdown.
                        The question is `%s`.
                        """.stripIndent().replace("\n", " "),
                theme, question);
        viewModel.setShortAnswerStatusCircleColor(Color.BLUE);
        supplyAsync(() -> Mdc.call(interactionId, () -> gptApi.send(askForShortAnswer)))
                .thenAccept(shortAnswerMd -> Mdc.run(interactionId, () -> {
                    var shortAnswerHtml = formatConverter.markdownToHtml(shortAnswerMd);
                    viewModel.setShortAnswer(shortAnswerHtml);
                    updateInteraction(interactionId, interaction -> interaction
                            .withAskForShortAnswer(askForShortAnswer)
                            .withShortAnswerMd(shortAnswerMd)
                            .withShortAnswerHtml(shortAnswerHtml));
                    soundService.beep2();
                    viewModel.setShortAnswerStatusCircleColor(Color.GREEN);
                    log.info("The short answer request finished.");
                })).handle((res, e) -> {
                    if (e != null) {
                        Mdc.run(interactionId, () -> viewModel.setShortAnswerStatusCircleColor(Color.RED));
                        return e;
                    } else {
                        return res;
                    }
                });
    }

    private void requestLongAnswer(String theme, String question, InteractionId interactionId) {
        log.info("Sending request for a long answer...");
        var askForLongAnswer = String.format("""
                        I will ask you a question about "%s".
                        You should answer with a detailed response.
                        Format your answer into Markdown.
                        The question is `%s`.
                        """.stripIndent().replace("\n", " "),
                theme, question);
        viewModel.setLongAnswerStatusCircleColor(Color.BLUE);
        supplyAsync(() -> Mdc.call(interactionId, () -> gptApi.send(askForLongAnswer)))
                .thenAccept(longAnswerMd -> Mdc.run(interactionId, () -> {
                    var longAnswerHtml = formatConverter.markdownToHtml(longAnswerMd);
                    viewModel.setLongAnswer(longAnswerHtml);
                    updateInteraction(interactionId, interaction -> interaction
                            .withAskForLongAnswer(askForLongAnswer)
                            .withLongAnswerMd(longAnswerMd)
                            .withLongAnswerHtml(longAnswerHtml));
                    soundService.beep3();
                    viewModel.setLongAnswerStatusCircleColor(Color.GREEN);
                    log.info("The long answer request finished.");
                })).handle((res, e) -> {
                    if (e != null) {
                        Mdc.run(interactionId, () -> viewModel.setLongAnswerStatusCircleColor(Color.RED));
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
