package lang.management.jmx.runtime_mxbean;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;

import static org.assertj.core.api.Assertions.assertThat;

class RuntimeMXBeanTest {
    @Test
    void getJvmName() {
        var jvmName = ManagementFactory.getRuntimeMXBean().getName();
        assertThat(jvmName).isNotBlank();
        System.out.printf("JVM Name: '%s'\n", jvmName);
    }
}
