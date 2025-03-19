package djl.onnxrs.load.file;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.onnxruntime.engine.OrtEngine;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.BasicTranslator;
import ai.djl.translate.TranslateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OnnxFileTest {
    private static final String MODEL_DIR_PATH = "/home/aleks/models/OpenSearch/sentence-transformers_msmarco-distilbert-base-tas-b-1.0.2-onnx";
    private static final String MODEL_FILE_PATH = MODEL_DIR_PATH + "/msmarco-distilbert-base-tas-b.onnx";

    @Test
    void byNewInstance() throws MalformedModelException, IOException, TranslateException {
        var modelPath = Paths.get(MODEL_DIR_PATH);
        assertThat(modelPath).exists();
        System.setProperty("ai.djl.default_engine", OrtEngine.ENGINE_NAME);
        try (var model = Model.newInstance("msmarco-distilbert-base-tas-b.onnx")) {
            model.load(modelPath, "msmarco-distilbert-base-tas-b.onnx");
            var translator = new BasicTranslator<String, float[]>(null, null);
            try (var predictor = model.newPredictor(translator)) {
                System.out.println("Model: " + model.getName());
                var input = "hello world";
                var embeddings = predictor.predict(input);
                System.out.println(Arrays.toString(embeddings));
            }
        }
    }

    @Test
    void byNewInstance2() throws MalformedModelException, IOException, TranslateException {
        try (Model model = Model.newInstance("sentence-transformer")) {
            model.load(Paths.get("/home/aleks/models/OpenSearch/sentence-transformers_msmarco-distilbert-base-tas-b-1.0.2-onnx"));
            System.out.println("Model loaded successfully!");
        }
    }

    @Test
    void byCriteria() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
//        var modelPath = Paths.get(MODEL_DIR_PATH);
        var modelPath = Paths.get(MODEL_FILE_PATH);
        assertThat(modelPath).exists();
        var criteria = Criteria.builder()
                .setTypes(String.class, float[].class)
                .optModelPath(modelPath)
                .optModelName("msmarco-distilbert-base-tas-b")
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