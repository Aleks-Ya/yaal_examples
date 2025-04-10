package djl.onnxrs.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import djl.LocalModelLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static djl.LocalModelLoader.PARAPHRASE_MP_NET_BASE_V_2_DIMENSION;
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
    void openSearch() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        try (var model = LocalModelLoader.paraphraseMpNetBaseV2();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            assertThat(embeddings).hasSize(PARAPHRASE_MP_NET_BASE_V_2_DIMENSION);
            System.out.println(Arrays.toString(embeddings));
        }
    }

    @Test
    void byMiniLM() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .optModelName("Xenova/all-MiniLM-L6-v2")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            assertThat(embeddings).hasSize(384);
            System.out.println(Arrays.toString(embeddings));
        }
    }

    @Test
    void bySentenceTransformersMiniLM() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .optModelName("sentence-transformers/paraphrase-MiniLM-L3-v2")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            assertThat(embeddings).hasSize(384);
            System.out.println(Arrays.toString(embeddings));
        }
    }

}