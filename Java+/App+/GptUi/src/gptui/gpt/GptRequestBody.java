package gptui.gpt;

import java.util.List;

record GptRequestBody(String model, List<GptMessage> messages, Double temperature) {
}
