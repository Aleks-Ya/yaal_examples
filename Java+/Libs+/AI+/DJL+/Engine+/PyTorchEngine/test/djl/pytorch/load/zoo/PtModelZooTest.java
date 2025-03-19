package djl.pytorch.load.zoo;

import ai.djl.pytorch.zoo.PtModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PtModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = PtModelZoo.listModels();
        Helper.printModels(models);
        assertThat(models).hasSize(11);
    }
}
