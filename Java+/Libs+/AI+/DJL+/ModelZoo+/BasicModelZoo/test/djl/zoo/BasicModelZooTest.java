package djl.zoo;

import ai.djl.basicmodelzoo.BasicModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BasicModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = BasicModelZoo.listModels();
        Helper.printModels(models);
    }
}
