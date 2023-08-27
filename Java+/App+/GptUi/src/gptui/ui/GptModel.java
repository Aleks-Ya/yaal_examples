package gptui.ui;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import gptui.gpt.GptApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class GptModel {
    private final GptApi gptApi = new GptApi();
    private final GptViewModel viewModel;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    GptModel(GptViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void sendQuestion(GptRequest gptRequest) {
        System.out.println("Question: " + gptRequest);
        executorService.submit(() -> {
            var longQuestionText = String.format("""
                    I'll ask you a question about '%s'.
                    Give more details about it.
                    The question is `%s`
                    """, gptRequest.theme(), gptRequest.question());
            var longAnswerMd = gptApi.send(longQuestionText);
            var mdDoc = parser.parse(longAnswerMd);
            var longAnswerHtml = renderer.render(mdDoc);
            viewModel.setLongAnswer(longAnswerHtml);
        });
        executorService.submit(() -> {
            var shortQuestionText = String.format("""
                    I'll ask you a question about '%s'.
                    You should answer with short response.
                    The question is `%s`
                    """, gptRequest.theme(), gptRequest.question());
            var shortAnswer = gptApi.send(shortQuestionText);
            viewModel.setShortAnswer(shortAnswer);
        });
        executorService.submit(() -> {
            var questionCorrectnessText = String.format("""
                    I'll give you a sentence.
                    Check if the sentence has grammatical mistakes.
                    it's ok if a sentence starts with 'How to'.
                    The sentence is `%s`""", gptRequest.question());
            var questionCorrectnessAnswer = gptApi.send(questionCorrectnessText);
            viewModel.setQuestionCorrectnessAnswer(questionCorrectnessAnswer);
        });
    }
}
