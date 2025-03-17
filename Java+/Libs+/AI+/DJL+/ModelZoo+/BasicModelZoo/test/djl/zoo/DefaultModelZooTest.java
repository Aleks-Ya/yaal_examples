package djl.zoo;

import ai.djl.repository.zoo.DefaultModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DefaultModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = DefaultModelZoo.listModels();
        Helper.printModels(models);
    }
}
