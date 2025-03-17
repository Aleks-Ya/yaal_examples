package djl.pytorch;

import ai.djl.Application;
import ai.djl.pytorch.engine.PtEngine;
import ai.djl.pytorch.zoo.PtModelZoo;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PtModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = PtModelZoo.listModels();
        Helper.printModels(models);
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
        Helper.printModels(models);
    }
}
