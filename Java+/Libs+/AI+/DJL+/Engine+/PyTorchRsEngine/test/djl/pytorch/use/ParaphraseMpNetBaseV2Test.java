package djl.pytorch.use;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import djl.LocalModelLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static djl.LocalModelLoader.PARAPHRASE_MP_NET_BASE_V_2_DIMENSION;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * <a href="https://huggingface.co/sentence-transformers/paraphrase-mpnet-base-v2">HuggingFace</a>
 * <a href="https://opensearch.org/docs/latest/ml-commons-plugin/pretrained-models/">OpenSearch</a>
 */
class ParaphraseMpNetBaseV2Test {

    @Test
    void generateEmbeddings() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        try (var model = LocalModelLoader.paraphraseMpNetBaseV2();
             var predictor = model.newPredictor()) {
            var inputText = "hello world";
            var embeddings = predictor.predict(inputText);
            System.out.println(Arrays.toString(embeddings));
            assertThat(embeddings).hasSize(PARAPHRASE_MP_NET_BASE_V_2_DIMENSION);
        }
    }

    @Test
    void exceedInputLength() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var inputLength = 1000000;
        var inputText = "a".repeat(inputLength);
        try (var model = LocalModelLoader.paraphraseMpNetBaseV2();
             var predictor = model.newPredictor()) {
            var embeddings = predictor.predict(inputText);
            assertThat(embeddings).hasSize(PARAPHRASE_MP_NET_BASE_V_2_DIMENSION);
        }
    }

}