package djl.api.repository;

import ai.djl.Device;
import ai.djl.engine.EngineException;
import ai.djl.util.cuda.CudaUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CudaUtilsTest {
    @Test
    void hasCuda() {
        assertThat(CudaUtils.hasCuda()).isFalse();
    }

    @Test
    void getCudaVersion() {
        assertThat(CudaUtils.getCudaVersion()).isEqualTo(12080);
        assertThat(CudaUtils.getCudaVersionString()).isEqualTo("128");
    }

    @Test
    void getGpuCount() {
        assertThat(CudaUtils.getGpuCount()).isEqualTo(0);
    }

    @Test
    void getComputeCapability() {
        assertThatThrownBy(() -> CudaUtils.getComputeCapability(Device.cpu().getDeviceId()))
                .isInstanceOf(EngineException.class)
                .hasMessage("CUDA API call failed: no CUDA-capable device is detected (100)");

        assertThatThrownBy(() -> CudaUtils.getComputeCapability(Device.gpu().getDeviceId()))
                .isInstanceOf(EngineException.class)
                .hasMessage("CUDA API call failed: no CUDA-capable device is detected (100)");
    }

}