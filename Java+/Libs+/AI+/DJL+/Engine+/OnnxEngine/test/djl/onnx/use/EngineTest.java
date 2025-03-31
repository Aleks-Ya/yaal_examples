package djl.onnx.use;

import ai.djl.engine.Engine;
import ai.djl.onnxruntime.engine.OrtEngine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EngineTest {

    @Test
    void listEngineNames() {
        var engineNames = Engine.getAllEngines();
        assertThat(engineNames).contains(OrtEngine.ENGINE_NAME);
    }

    @Test
    void getEngine() {
        var engine = Engine.getEngine(OrtEngine.ENGINE_NAME);
        assertThat(engine.getEngineName()).isEqualTo(OrtEngine.ENGINE_NAME);
    }

    @Test
    void gpuCountAllEngines() {
        Engine.getAllEngines().stream()
                .map(Engine::getEngine)
                .map(engine -> engine.getEngineName() + " " + engine.getGpuCount())
                .forEach(System.out::println);
    }

    @Test
    void gpuCount() {
        var count = Engine.getInstance().getGpuCount();
        assertThat(count).isEqualTo(0);
    }
}