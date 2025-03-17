package djl.onnx;

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
}
