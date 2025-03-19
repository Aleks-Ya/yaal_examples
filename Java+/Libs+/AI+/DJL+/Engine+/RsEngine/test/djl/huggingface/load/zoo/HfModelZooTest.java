package djl.huggingface.load.zoo;

import ai.djl.huggingface.zoo.HfModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HfModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = HfModelZoo.listModels();
        Helper.printModels(models);
        assertThat(models).hasSize(6);
    }
}
