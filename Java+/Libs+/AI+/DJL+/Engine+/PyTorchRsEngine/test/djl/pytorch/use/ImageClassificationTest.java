package djl.pytorch.use;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;

class ImageClassificationTest {
    @Test
    void predict() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                .optArtifactId("resnet")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            var imagePath = ResourceUtil.resourceToPath("use/pencil.png");
            var image = ImageFactory.getInstance().fromFile(imagePath);
            var classifications = predictor.predict(image);
            System.out.println(classifications);
        }
    }
}
