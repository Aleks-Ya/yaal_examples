from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ModelParam


def test_create_message(client: Anthropic, model: ModelParam, max_tokens: int):
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[{
            "role": "user",
            "content": "Return just number 42"
        }]
    )
    print(message.model_dump_json())
    assert message.content[0].text == "42"


def test_create_message_array(client: Anthropic, model: ModelParam, max_tokens: int):
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[{
            "role": "user",
            "content": [{
                "type": "text",
                "text": "Return just number 42"
            }]
        }]
    )
    print(message.model_dump_json())
    assert message.content[0].text == "42"


def test_create_message_text_block_param(client: Anthropic, model: ModelParam, max_tokens: int):
    text_block_params: TextBlockParam = TextBlockParam(type="text", text="Return just number 42")
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[
            {
                "role": "user",
                "content": [text_block_params]
            }
        ]
    )
    print(message.model_dump_json())
    assert message.content[0].text == "42"


def test_create_message_message_param(client: Anthropic, model: ModelParam, max_tokens: int):
    text_block_param: TextBlockParam = TextBlockParam(type="text", text="Return just number 42")
    message_param: MessageParam = MessageParam(role="user", content=[text_block_param])
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param]
    )
    print(message.model_dump_json())
    assert message.content[0].text == "42"
