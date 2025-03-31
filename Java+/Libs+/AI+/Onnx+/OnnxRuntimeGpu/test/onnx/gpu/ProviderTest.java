package onnx.gpu;

import ai.onnxruntime.OrtEnvironment;
import org.junit.jupiter.api.Test;

class ProviderTest {
    @Test
    void listAvailableEngines() {
        var providers = OrtEnvironment.getAvailableProviders();
        System.out.println("Available execution providers: " + providers);
    }
}
