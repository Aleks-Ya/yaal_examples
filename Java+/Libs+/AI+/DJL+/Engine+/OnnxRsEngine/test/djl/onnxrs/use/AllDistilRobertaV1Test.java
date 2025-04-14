package djl.onnxrs.use;

import ai.djl.MalformedModelException;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static djl.LocalModelLoader.DIMENSION_768;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * <a href="https://huggingface.co/sentence-transformers/all-distilroberta-v1">HuggingFace</a>
 * <a href="https://opensearch.org/docs/latest/ml-commons-plugin/pretrained-models/">OpenSearch</a>
 */
class AllDistilRobertaV1Test {

    @Test
    void generateEmbeddingsZoo() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(ai.djl.Application.NLP.TEXT_EMBEDDING)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optArtifactId("sentence-transformers/all-distilroberta-v1")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            assertThat(model.getModelPath()).endsWith(Path.of("ai/djl/huggingface/onnxruntime/sentence-transformers/all-distilroberta-v1/0.0.1/all-distilroberta-v1"));
            var inputText = "hello";
            var embeddings = predictor.predict(inputText);
            System.out.println(Arrays.toString(embeddings));
            assertThat(embeddings).hasSize(DIMENSION_768);
        }
    }

}