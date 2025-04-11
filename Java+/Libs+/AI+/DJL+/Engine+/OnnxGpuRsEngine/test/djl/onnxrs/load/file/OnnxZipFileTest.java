package djl.onnxrs.load.file;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import djl.LocalModels;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OnnxZipFileTest {
    @Test
    void byNewInstance() throws MalformedModelException, IOException, TranslateException {
        var modelPath = Paths.get("/home/aleks/models/OpenSearch/sentence-transformers_msmarco-distilbert-base-tas-b-1.0.2-onnx.zip");
        assertThat(modelPath).exists();
        try (var model = Model.newInstance("embedding")) {
            model.load(modelPath);
            try (var predictor = model.newPredictor(null)) {
                var image = ImageFactory.getInstance().fromFile(Paths.get("/home/aleks/Downloads/house-1.jpg"));
                var classifications = predictor.predict(image);
                System.out.println(classifications);
            }
        }
    }

    @Test
    void byCriteria() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optModelPath(LocalModels.OpenSearch.PARAPHRASE_MPNET_BASE_V2_ZIP)
                .optEngine(OrtEngine.ENGINE_NAME)
                .optModelName("paraphrase-mpnet-base-v2")
                .build();
        try (var model = criteria.loadModel();
             var predictor = model.newPredictor()) {
            System.out.println("Model: " + model.getName());
            var input = "hello world";
            var embeddings = predictor.predict(input);
            System.out.println(Arrays.toString(embeddings));
        }
    }
}