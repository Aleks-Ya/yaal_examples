package djl.onnx.use;

import ai.djl.Application;
import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NDManagerTest {
    @Test
    void modelDevice() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optApplication(Application.NLP.TEXT_EMBEDDING)
                .build();
        try (var model = criteria.loadModel()) {
            var ndManager = model.getNDManager();
            var defaultDevice = ndManager.defaultDevice();
            var device = ndManager.getDevice();
            assertThat(defaultDevice).isEqualTo(Device.cpu());
            assertThat(device).isEqualTo(Device.cpu());
        }
    }

}