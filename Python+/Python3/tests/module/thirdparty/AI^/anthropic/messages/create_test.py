from anthropic import Anthropic
from anthropic.types import Message


def test_create_message(client: Anthropic, model: str):
    message: Message = client.messages.create(
        model=model,
        max_tokens=1024,
        messages=[
            {
                "role": "user",
                "content": "Hello, Claude",
            }
        ]
    )
    json: str = message.model_dump_json()
    print(json)
    assert json is not None
