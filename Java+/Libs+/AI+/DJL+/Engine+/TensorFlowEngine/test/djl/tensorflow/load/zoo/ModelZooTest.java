package djl.tensorflow.load.zoo;

import ai.djl.Application;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.tensorflow.engine.TfEngine;
import ai.djl.tensorflow.zoo.TfModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = ModelZoo.listModels();
        Helper.printModels(models);
    }

    @Test
    void getModelZoo() {
        var zoo = ModelZoo.getModelZoo(TfModelZoo.GROUP_ID);
        var engines = zoo.getSupportedEngines();
        assertThat(engines).containsOnly(TfEngine.ENGINE_NAME);
    }

    @Test
    void getImageClassificationModels() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                .build();
        var models = ModelZoo.listModels(criteria);
        assertThat(Helper.toMap(models))
                .hasSize(1)
                .containsEntry(
                        Application.CV.IMAGE_CLASSIFICATION.getPath(),
                        List.of("resnet50", "mobilenet"));
    }

    @Test
    void getTextEmbeddingModels() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        var models = ModelZoo.listModels(criteria);
        assertThat(models).isEmpty();
    }
}
