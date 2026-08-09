from anthropic import Anthropic
from anthropic.types import MessageTokensCount, MessageParam, ModelParam


def test_count_tokens(client: Anthropic, model: ModelParam):
    response: MessageTokensCount = client.messages.count_tokens(
        model=model,
        system="You are a scientist",
        messages=[MessageParam(
            role="user",
            content="Hello, Claude"
        )],
    )
    print(response.model_dump_json())
    assert response.input_tokens == 20
