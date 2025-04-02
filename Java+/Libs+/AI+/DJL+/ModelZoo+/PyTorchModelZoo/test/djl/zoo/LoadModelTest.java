package djl.zoo;

import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class LoadModelTest {
    @Test
    void loadModel() throws ModelNotFoundException, MalformedModelException, IOException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .build();
        try (var model = criteria.loadModel()) {
            var path = model.getModelPath();
            System.out.println(path);
        }
    }
}
