package djl.onnx.use;

import ai.djl.Device;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceTest {

    @Test
    void cpuDeviceEnum() {
        var device = Device.cpu();
        assertThat(device.getDeviceId()).isEqualTo(-1);
        assertThat(device.getDeviceType()).isEqualTo("cpu");
        assertThat(device.getDevices()).containsOnly(device);
        assertThat(device.isGpu()).isFalse();
    }

    @Test
    void gpuDeviceEnum() {
        var device = Device.gpu();
        assertThat(device.getDeviceId()).isEqualTo(0);
        assertThat(device.getDeviceType()).isEqualTo("gpu");
        assertThat(device.getDevices()).containsOnly(device);
        assertThat(device.isGpu()).isTrue();
    }

    @Test
    void fromName() {
        assertThat(Device.fromName("cpu")).isEqualTo(Device.cpu());
        assertThat(Device.fromName("gpu")).isEqualTo(Device.gpu());
    }

    @Test
    void multiDevice() {
        var device = new Device.MultiDevice(Device.cpu(), Device.gpu());
        assertThat(device.getDeviceId()).isEqualTo(-1);
        assertThat(device.getDeviceType()).isEqualTo("cpu-1+gpu0");
        assertThat(device.getDevices()).containsExactlyInAnyOrder(Device.cpu(), Device.gpu());
        assertThat(device.isGpu()).isFalse();
    }

}