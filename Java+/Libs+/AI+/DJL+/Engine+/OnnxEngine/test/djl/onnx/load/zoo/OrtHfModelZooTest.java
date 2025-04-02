package djl.onnx.load.zoo;

import ai.djl.Application;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.onnxruntime.zoo.OrtHfModelZoo;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class OrtHfModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = OrtHfModelZoo.listModels();
        Helper.printModels(models);
        assertThat(models).hasSize(12);
    }

    @Test
    void getModelZoo() {
        var zoo = ModelZoo.getModelZoo("ai.djl.huggingface.onnxruntime");
        var engines = zoo.getSupportedEngines();
        assertThat(engines).containsOnly(OrtEngine.ENGINE_NAME);
    }

    @Test
    void getTextEmbeddingModels() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
        assertThat(models).containsOnlyKeys(Application.NLP.TEXT_EMBEDDING);
    }

    @Test
    void getModelByName() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optModelName("resnet18_v1-7") //NOT WORKING: returns all models
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
        assertThat(models).hasSize(12);
    }
}
