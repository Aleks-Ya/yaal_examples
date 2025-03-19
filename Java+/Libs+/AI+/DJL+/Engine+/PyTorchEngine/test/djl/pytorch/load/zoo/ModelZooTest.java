package djl.pytorch.load.zoo;

import ai.djl.Application;
import ai.djl.pytorch.engine.PtEngine;
import ai.djl.pytorch.zoo.PtModelZoo;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
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
        assertThat(models).hasSize(11);
    }

    @Test
    void getModelZoo() {
        var zoo = ModelZoo.getModelZoo(PtModelZoo.GROUP_ID);
        var engines = zoo.getSupportedEngines();
        assertThat(engines).containsOnly(PtEngine.ENGINE_NAME);
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
                        List.of("resnet18_embedding", "traced_resnet50", "traced_resnet18", "resnet101_v1"));
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
