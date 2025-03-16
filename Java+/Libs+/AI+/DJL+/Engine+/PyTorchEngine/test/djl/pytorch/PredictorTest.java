package djl.pytorch;

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

class PredictorTest {
    @Test
    void predict() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            var image = ImageFactory.getInstance().fromFile(Paths.get("/home/aleks/Downloads/house-1.jpg"));
            var classifications = predictor.predict(image);
            System.out.println(classifications);
        }
    }
}
