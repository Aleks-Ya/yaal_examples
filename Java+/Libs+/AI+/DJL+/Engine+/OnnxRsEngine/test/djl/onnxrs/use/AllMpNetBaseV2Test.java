package djl.onnxrs.use;

import ai.djl.MalformedModelException;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import djl.LocalModelLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static djl.LocalModelLoader.DIMENSION_768;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * <a href="https://huggingface.co/sentence-transformers/all-mpnet-base-v2">HuggingFace</a>
 * <a href="https://opensearch.org/docs/latest/ml-commons-plugin/pretrained-models/">OpenSearch</a>
 */
class AllMpNetBaseV2Test {

    @Test
    void generateEmbeddingsFromZoo() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(ai.djl.Application.NLP.TEXT_EMBEDDING)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optArtifactId("sentence-transformers/all-mpnet-base-v2")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            assertThat(model.getModelPath()).endsWith(Path.of("ai/djl/huggingface/onnxruntime/sentence-transformers/all-mpnet-base-v2/0.0.1/all-mpnet-base-v2"));
            var inputText = "hello";
            var embeddings = predictor.predict(inputText);
            System.out.println(Arrays.toString(embeddings));
            assertThat(embeddings).hasSize(DIMENSION_768);
        }
    }

    @Test
    void generateEmbeddingsFromZip() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        try (var model = LocalModelLoader.allMpNetBaseV2();
             var predictor = model.newPredictor()) {
            assertThat(model.getModelPath()).endsWith(Path.of("sentence-transformers_all-mpnet-base-v2-1.0.1-onnx"));
            var inputText = "hello";
            var embeddings = predictor.predict(inputText);
            System.out.println(Arrays.toString(embeddings));
            assertThat(embeddings).hasSize(DIMENSION_768);
        }
    }

}