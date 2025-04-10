package djl.huggingface.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HuggingFaceTokenizerTest {

    @Test
    void tokenize() throws ModelNotFoundException, MalformedModelException, IOException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        var arguments = Map.<String, Object>of();
        try (var model = criteria.loadModel();
             var tokenizer = HuggingFaceTokenizer.builder(arguments)
                     .optTokenizerPath(model.getModelPath())
                     .optManager(model.getNDManager())
                     .build()) {
            var tokens = tokenizer.tokenize("hello world");
            assertThat(tokens).containsExactly("[CLS]", "hello", "world", "[SEP]");
        }
    }

}