package gptui.ui;

import gptui.gpt.GptApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class GptModel {
    private final GptApi gptApi = new GptApi();
    private final GptViewModel viewModel;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

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
            var longAnswer = gptApi.send(longQuestionText);
            viewModel.setLongAnswer(longAnswer);
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
