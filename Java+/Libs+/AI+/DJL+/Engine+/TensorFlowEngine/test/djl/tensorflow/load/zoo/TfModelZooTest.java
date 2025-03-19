package djl.tensorflow.load.zoo;

import ai.djl.repository.zoo.ModelNotFoundException;
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
        assertThat(models).hasSize(2);
    }
}
