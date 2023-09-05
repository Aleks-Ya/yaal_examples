package gptui.ui;

import gptui.Mdc;
import gptui.format.FormatConverter;
import gptui.format.ThemeHelper;
import gptui.gpt.GptApi;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

class GptModel {
    private static final Logger log = LoggerFactory.getLogger(GptModel.class);
    private final GptApi gptApi = new GptApi();
    private final FormatConverter formatConverter = new FormatConverter();
    private final ThemeHelper themeHelper = new ThemeHelper();
    private final GptViewModel viewModel;
    private final GptStorage gptStorage = new GptStorage();

    GptModel(GptViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.themeListUpdated(readThemeList());
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), null);
    }

    private void updateInteraction(InteractionId interactionId, Function<Interaction, Interaction> update) {
        gptStorage.updateInteraction(interactionId, update);
        var currentInteraction = gptStorage.readInteraction(interactionId).orElseThrow();
        viewModel.interactionHistoryUpdated(gptStorage.readAllInteractions(), currentInteraction);
        viewModel.themeListUpdated(readThemeList());
    }

    public void sendQuestion(String theme, String question) {
        var interactionId = gptStorage.newInteractionId();
        Mdc.run(() -> {
            log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
            viewModel.setQuestionCorrectnessAnswer("");
            viewModel.setShortAnswer("");
            viewModel.setLongAnswer("");
            updateInteraction(interactionId, interaction -> interaction
                    .withTheme(theme)
                    .withQuestion(question));
            log.info("Sending request for a long answer...");
            var askForLongAnswer = String.format("""
                            I will ask you a question about "%s".
                            You should answer with a detailed response.
                            Format your answer into Markdown.
                            The question is `%s`.
                            """.stripIndent().replace("\n", " "),
                    theme, question);
            supplyAsync(() -> Mdc.call(() -> gptApi.send(askForLongAnswer), interactionId))
                    .thenAccept(longAnswerMd -> Mdc.run(() -> {
                        var longAnswerHtml = formatConverter.markdownToHtml(longAnswerMd);
                        viewModel.setLongAnswer(longAnswerHtml);
                        updateInteraction(interactionId, interaction -> interaction
                                .withAskForLongAnswer(askForLongAnswer)
                                .withLongAnswerMd(longAnswerMd)
                                .withLongAnswerHtml(longAnswerHtml));
                        log.info("The long answer request finished.");
                    }, interactionId));

            log.info("Sending request for a shot answer...");
            var askForShortAnswer = String.format("""
                            I will ask you a question about "%s".
                            You should answer with a short response.
                            Format your answer into Markdown.
                            The question is `%s`.
                            """.stripIndent().replace("\n", " "),
                    theme, question);
            supplyAsync(() -> Mdc.call(() -> gptApi.send(askForShortAnswer), interactionId))
                    .thenAccept(shortAnswerMd -> Mdc.run(() -> {
                        var shortAnswerHtml = formatConverter.markdownToHtml(shortAnswerMd);
                        viewModel.setShortAnswer(shortAnswerMd);
                        updateInteraction(interactionId, interaction -> interaction
                                .withAskForShortAnswer(askForShortAnswer)
                                .withShortAnswerMd(shortAnswerMd)
                                .withShortAnswerHtml(shortAnswerHtml));
                        log.info("The short answer request finished.");
                    }, interactionId));

            log.info("Sending request for a question correctness...");
            var askForQuestionCorrectness = String.format("""
                            I will give you a sentence.
                            Check if the sentence has grammatical mistakes.
                            It is not a mistake if the sentence starts with "How to".
                            The sentence is `%s`.""".stripIndent().replace("\n", " "),
                    question);
            supplyAsync(() -> Mdc.call(() -> gptApi.send(askForQuestionCorrectness), interactionId))
                    .thenAccept(questionCorrectnessAnswer -> Mdc.run(() -> {
                        viewModel.setQuestionCorrectnessAnswer(questionCorrectnessAnswer);
                        updateInteraction(interactionId, interaction -> interaction
                                .withAskForQuestionCorrectness(askForQuestionCorrectness)
                                .withQuestionCorrectnessAnswer(questionCorrectnessAnswer));
                        log.info("The question correctness answer request finished.");
                    }, interactionId));
        }, interactionId);
    }

    private List<String> readThemeList() {
        return themeHelper.interactionsToThemeList(gptStorage.readAllInteractions());
    }
}
