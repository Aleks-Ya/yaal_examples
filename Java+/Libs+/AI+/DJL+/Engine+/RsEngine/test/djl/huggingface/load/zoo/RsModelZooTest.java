package djl.huggingface.load.zoo;

import ai.djl.engine.rust.zoo.RsModelZoo;
import ai.djl.repository.zoo.ModelNotFoundException;
import djl.Helper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RsModelZooTest {
    @Test
    void listModelsAll() throws ModelNotFoundException, IOException {
        var models = RsModelZoo.listModels();
        Helper.printModels(models);
        assertThat(models).hasSize(6);
    }
}
