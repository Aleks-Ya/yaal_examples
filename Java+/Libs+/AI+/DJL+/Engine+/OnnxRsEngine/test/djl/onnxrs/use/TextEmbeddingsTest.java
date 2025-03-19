package djl.onnxrs.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
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
    void openSearch() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var modelPath = Paths.get("/home/aleks/models/OpenSearch/sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx.zip");
        assertThat(modelPath).exists();
        var translatorFactory = new TextEmbeddingTranslatorFactory();
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optTranslatorFactory(translatorFactory)
                .optModelPath(modelPath)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optModelName("paraphrase-mpnet-base-v2")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            System.out.println(Arrays.toString(embeddings));
        }
    }

}