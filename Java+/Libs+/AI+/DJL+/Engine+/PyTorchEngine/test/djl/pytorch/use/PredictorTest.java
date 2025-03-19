package djl.pytorch.use;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PredictorTest {
    @Test
    void textEmbeddingPredict() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .build();

        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            String inputText = "The quick brown fox jumps over the lazy dog.";
            var embeddings = predictor.predict(inputText);
            System.out.println("Text Embeddings: ");
            for (float value : embeddings) {
                System.out.print(value + " ");
            }
        }
    }
}