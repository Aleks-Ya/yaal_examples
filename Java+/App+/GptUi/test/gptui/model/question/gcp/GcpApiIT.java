package gptui.model.question.gcp;

import com.google.inject.Guice;
import gptui.model.config.ConfigurationModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GcpApiIT {
    private final GcpApi api = Guice.createInjector(new ConfigurationModule(), new GcpModule())
            .getInstance(GcpApi.class);

    @Test
    void send() {
        var response = api.send("What is the last Java version?", 50);
        System.out.println(response);
    }

    @Test
    void error() {
        assertThatThrownBy(() -> api.send(null, 50))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("INVALID_ARGUMENT");
    }
}