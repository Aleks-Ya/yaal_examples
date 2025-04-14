package djl.onnxrs.load.zoo;

import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.onnxruntime.zoo.OrtModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HuggingFace models for ONNX.
 */
class OrtModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = OrtModelZoo.listModels();
        Helper.printModels(models);
    }

    @Test
    void getModelZoo() {
        var zoo = ModelZoo.getModelZoo(OrtModelZoo.GROUP_ID);
        var engines = zoo.getSupportedEngines();
        assertThat(engines).containsOnly(OrtEngine.ENGINE_NAME);
    }
}
