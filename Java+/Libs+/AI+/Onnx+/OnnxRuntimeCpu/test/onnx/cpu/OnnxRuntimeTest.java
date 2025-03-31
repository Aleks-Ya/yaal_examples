package onnx.cpu;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

class OnnxRuntimeTest {
    @Test
    void useModelFromLocalFile() throws OrtException {
        try (var env = OrtEnvironment.getEnvironment();
             var options = new OrtSession.SessionOptions()) {
            var modelPath = "/home/aleks/models/OpenSearch/sentence-transformers_paraphrase-mpnet-base-v2-1.0.0-onnx/paraphrase-mpnet-base-v2.onnx";
            try (var session = env.createSession(modelPath, options)) {
                long[][] inputData = new long[][]{{1L, 2L, 3L, 4L}};
                var inputTensor = OnnxTensor.createTensor(env, inputData);
                long[][] attentionMaskData = new long[][]{{1L, 1L, 1L, 1L}};
                var attentionMask = OnnxTensor.createTensor(env, attentionMaskData);
                var result = session.run(Map.of("input_ids", inputTensor, "attention_mask", attentionMask));
                var outputData = (float[][][]) result.get(0).getValue();
                System.out.println("Model output: " + Arrays.toString(outputData));
            }

        }
    }

}
