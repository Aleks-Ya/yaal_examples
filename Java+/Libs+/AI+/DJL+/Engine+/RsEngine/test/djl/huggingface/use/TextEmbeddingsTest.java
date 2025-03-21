package djl.huggingface.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class TextEmbeddingsTest {
    @Test
    void generateTextEmbeddings() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            System.out.println(Arrays.toString(embeddings));
        }
    }

    @Test
    void msmarcoDistilbertBaseV4() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var modelName = "sentence-transformers/msmarco-distilbert-base-v4";
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .optModelName(modelName)
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            assertThat(model.getName()).isEqualTo(modelName);
            var input = "hello world";
            var embeddings = predictor.predict(input);
            assertThat(embeddings).hasSize(384);
            System.out.println(Arrays.toString(embeddings));
        }
    }
}