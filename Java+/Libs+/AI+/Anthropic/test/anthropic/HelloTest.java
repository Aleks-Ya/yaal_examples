package anthropic;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import org.junit.jupiter.api.Test;

class HelloTest {
    @Test
    void hello() {
        AnthropicClient client = AnthropicOkHttpClient.fromEnv();
        MessageCreateParams params = MessageCreateParams.builder()
                .maxTokens(1024L)
                .addUserMessage("Hello, Claude")
                .model(Model.CLAUDE_OPUS_4_8)
                .build();
        Message message = client.messages().create(params);
        System.out.println(message);
    }

}
