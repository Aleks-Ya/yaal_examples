package djl.huggingface.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TextEmbeddingTranslatorFactoryTest {

    @Test
    void newTranslator() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var inputClass = String.class;
        var outputClass = float[].class;
        var criteria = Criteria.builder()
                .setTypes(inputClass, outputClass)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        var arguments = Map.<String, Object>of();
        try (var model = criteria.loadModel()) {
            var translatorFactory = new TextEmbeddingTranslatorFactory();
            var translator = translatorFactory.newInstance(inputClass, outputClass, model, arguments);
            assertThat(translator).isNotNull();
        }
    }

}