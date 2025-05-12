package gptui.model.question.openai;

import com.google.inject.Guice;
import gptui.model.config.ConfigurationModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GptApiIT {
    private final GptApi api = Guice.createInjector(new ConfigurationModule(), new OpenAiModule())
            .getInstance(GptApi.class);

    @Test
    void send() {
        var response = api.send("What is the last Java version?", 50);
        System.out.println(response);
    }

    @Test
    void error() {
        assertThatThrownBy(() -> api.send(null, 50))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("invalid_request_error");
    }
}