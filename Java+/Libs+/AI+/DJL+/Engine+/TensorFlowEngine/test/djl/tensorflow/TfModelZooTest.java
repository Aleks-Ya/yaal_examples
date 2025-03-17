package djl.tensorflow;

import ai.djl.Application;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.tensorflow.engine.TfEngine;
import ai.djl.tensorflow.zoo.TfModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class TfModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = TfModelZoo.listModels();
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
        Helper.printModels(models);
    }
}
