package gptui.ui;

import gptui.format.FormatConverter;
import gptui.gpt.GptApi;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;

class GptModel {
    private static final Logger log = LoggerFactory.getLogger(GptModel.class);
    private final GptApi gptApi = new GptApi();
    private final FormatConverter formatConverter = new FormatConverter();
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
    }

    public void sendQuestion(String theme, String question) {
        log.info("Sending question: theme=\"{}\", question=\"{}\"", theme, question);
        viewModel.setQuestionCorrectnessAnswer("");
        viewModel.setShortAnswer("");
        viewModel.setLongAnswer("");
        var interactionId = gptStorage.newInteractionId();
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
        gptApi.send(askForLongAnswer).thenAccept(longAnswerMd -> {
            var longAnswerHtml = formatConverter.markdownToHtml(longAnswerMd);
            viewModel.setLongAnswer(longAnswerHtml);
            updateInteraction(interactionId, interaction -> interaction
                    .withAskForLongAnswer(askForLongAnswer)
                    .withLongAnswerMd(longAnswerMd)
                    .withLongAnswerHtml(longAnswerHtml));
            log.info("The long answer request finished.");
        });

        log.info("Sending request for a shot answer...");
        var askForShortAnswer = String.format("""
                        I will ask you a question about "%s".
                        You should answer with a short response.
                        Format your answer into Markdown.
                        The question is `%s`.
                        """.stripIndent().replace("\n", " "),
                theme, question);
        gptApi.send(askForShortAnswer).thenAccept(shortAnswerMd -> {
            var shortAnswerHtml = formatConverter.markdownToHtml(shortAnswerMd);
            viewModel.setShortAnswer(shortAnswerMd);
            updateInteraction(interactionId, interaction -> interaction
                    .withAskForShortAnswer(askForShortAnswer)
                    .withShortAnswerMd(shortAnswerMd)
                    .withShortAnswerHtml(shortAnswerHtml));
            log.info("The short answer request finished.");
        });

        log.info("Sending request for a question correctness...");
        var askForQuestionCorrectness = String.format("""
                        I will give you a sentence.
                        Check if the sentence has grammatical mistakes.
                        It is not a mistake if the sentence starts with "How to".
                        The sentence is `%s`.""".stripIndent().replace("\n", " "),
                question);
        gptApi.send(askForQuestionCorrectness).thenAccept(questionCorrectnessAnswer -> {
            viewModel.setQuestionCorrectnessAnswer(questionCorrectnessAnswer);
            updateInteraction(interactionId, interaction -> interaction
                    .withAskForQuestionCorrectness(askForQuestionCorrectness)
                    .withQuestionCorrectnessAnswer(questionCorrectnessAnswer));
            log.info("The question correctness answer request finished.");
        });
    }

    private List<String> readThemeList() {
        return gptStorage.readAllInteractions().stream()
                .collect(groupingBy(Interaction::theme)).entrySet().stream().map(entry -> {
                    var theme = entry.getKey();
                    var latestInteraction = entry.getValue().stream()
                            .max(comparing(interaction -> interaction.id().id()))
                            .orElseThrow();
                    return entry(theme, latestInteraction);
                })
                .sorted(Comparator.<Map.Entry<String, Interaction>, Long>comparing(entry -> entry.getValue().id().id()).reversed())
                .map(Map.Entry::getKey)
                .toList();
    }
}
