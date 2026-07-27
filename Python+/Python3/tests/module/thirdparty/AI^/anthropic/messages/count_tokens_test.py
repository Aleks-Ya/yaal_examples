from anthropic import Anthropic
from anthropic.types import MessageTokensCount


def test_count_tokens(client: Anthropic, model: str):
    response: MessageTokensCount = client.messages.count_tokens(
        model=model,
        system="You are a scientist",
        messages=[{"role": "user", "content": "Hello, Claude"}],
    )
    json: str = response.model_dump_json()
    print(json)
    assert json is not None
