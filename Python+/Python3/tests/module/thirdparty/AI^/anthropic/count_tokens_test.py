from anthropic import Anthropic
from anthropic.types import MessageTokensCount


def test_count_tokens():
    client: Anthropic = Anthropic()
    response: MessageTokensCount = client.messages.count_tokens(
        model="claude-opus-4-8",
        system="You are a scientist",
        messages=[{"role": "user", "content": "Hello, Claude"}],
    )
    json: str = response.model_dump_json()
    print(json)
    assert json is not None
