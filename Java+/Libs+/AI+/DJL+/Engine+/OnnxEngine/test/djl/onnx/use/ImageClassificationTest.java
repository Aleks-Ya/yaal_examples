package djl.onnx.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ImageClassificationTest {
    @Test
    void predict() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            var imagePath = Paths.get("/home/aleks/Downloads/house-1.jpg");
            assertThat(imagePath).exists();
            var image = ImageFactory.getInstance().fromFile(imagePath);
            var classifications = predictor.predict(image);
            System.out.println(classifications);
        }
    }
}
