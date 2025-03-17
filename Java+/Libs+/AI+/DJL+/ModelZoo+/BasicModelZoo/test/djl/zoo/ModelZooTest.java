package djl.zoo;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = ModelZoo.listModels();
        Helper.printModels(models);
    }

    @Test
    void listModelsByTypes() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
    }

    /**
     * Needs PyTorch dependency.
     */
    @Test
    void listModelsByEnginePyTorch() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optEngine("PyTorch")
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
    }

    /**
     * Needs Onnx dependency.
     */
    @Test
    void listModelsByEngineOnnx() throws ModelNotFoundException, IOException {
        var criteria = Criteria.builder()
                .optEngine("OnnxRuntime")
                .build();
        var models = ModelZoo.listModels(criteria);
        Helper.printModels(models);
    }
}
