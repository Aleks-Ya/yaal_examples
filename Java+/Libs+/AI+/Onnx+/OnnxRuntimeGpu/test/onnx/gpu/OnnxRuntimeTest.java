package onnx.gpu;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import djl.LocalModels;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

class OnnxRuntimeTest {
    @Test
    void useModelFromLocalFile() throws OrtException {
        try (var env = OrtEnvironment.getEnvironment();
             var options = new OrtSession.SessionOptions()) {
            var modelPath = LocalModels.OpenSearch.PARAPHRASE_MPNET_BASE_V2_ONNX.toString();
            try (var session = env.createSession(modelPath, options)) {
                var inputData = new long[][]{{1L, 2L, 3L, 4L}};
                var inputTensor = OnnxTensor.createTensor(env, inputData);
                var attentionMaskData = new long[][]{{1L, 1L, 1L, 1L}};
                var attentionMask = OnnxTensor.createTensor(env, attentionMaskData);
                var result = session.run(Map.of("input_ids", inputTensor, "attention_mask", attentionMask));
                var outputData = (float[][][]) result.get(0).getValue();
                System.out.println("Model output: " + Arrays.toString(outputData));
            }
        }
    }

}
