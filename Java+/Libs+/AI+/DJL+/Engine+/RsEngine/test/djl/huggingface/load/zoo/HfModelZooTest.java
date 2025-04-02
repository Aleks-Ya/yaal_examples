package djl.huggingface.load.zoo;

import ai.djl.Application.NLP;
import ai.djl.engine.rust.RsEngine;
import ai.djl.huggingface.zoo.HfModelZoo;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HfModelZooTest {

    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = HfModelZoo.listModels();
        Helper.printModels(models);
        assertThat(models).containsOnlyKeys(
                NLP.FILL_MASK,
                NLP.QUESTION_ANSWER,
                NLP.TEXT_CLASSIFICATION,
                NLP.TOKEN_CLASSIFICATION,
                NLP.ZERO_SHOT_CLASSIFICATION,
                NLP.TEXT_EMBEDDING);
    }

    @Test
    void pyTorchModelsInHf() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optEngine(RsEngine.ENGINE_NAME)
                .build();
        var models = HfModelZoo.listModels(criteria);
        Helper.printModels(models);
        assertThat(models).containsOnlyKeys(
                NLP.FILL_MASK,
                NLP.QUESTION_ANSWER,
                NLP.TEXT_CLASSIFICATION,
                NLP.TOKEN_CLASSIFICATION,
                NLP.ZERO_SHOT_CLASSIFICATION,
                NLP.TEXT_EMBEDDING);
    }

    @Test
    void pyTorchTextEmbeddingModelsInHf() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(NLP.TEXT_EMBEDDING)
                .optEngine(RsEngine.ENGINE_NAME)
                .build();
        var models = HfModelZoo.listModels(criteria);
        Helper.printModels(models);
        assertThat(models).containsOnlyKeys(NLP.TEXT_EMBEDDING);
    }
}
