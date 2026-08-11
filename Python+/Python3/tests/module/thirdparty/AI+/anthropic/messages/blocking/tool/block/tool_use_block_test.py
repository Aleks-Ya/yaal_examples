from anthropic import Anthropic
from anthropic.types import Message, ToolParam, ContentBlock, ToolUseBlock, TextBlockParam, MessageParam, ModelParam


def test_single_tool_use_block(client: Anthropic, model: str, max_tokens: int):
    tool_param: ToolParam = ToolParam(
        name="get_magic_number",
        description="Get the magic number.",
        input_schema={"type": "object"}
    )
    text_block_param: TextBlockParam = TextBlockParam(
        type="text",
        text="What's the magic number? Print only the number."
    )
    message_param: MessageParam = MessageParam(
        role="user",
        content=[text_block_param]
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param],
        tools=[tool_param]
    )
    print(message.content)
    assert len(message.content) == 1

    block0: ContentBlock = message.content[0]
    assert type(block0) == ToolUseBlock
    assert block0.type == "tool_use"
    assert block0.name == "get_magic_number"


def test_multiple_tool_use_blocks(client: Anthropic, model: ModelParam, max_tokens: int):
    tool_param: ToolParam = ToolParam(
        name="sum",
        description="Adds two numbers together and returns the result.",
        input_schema={
            "type": "object",
            "properties": {
                "left": {
                    "type": "number",
                    "description": "The first number to add.",
                },
                "right": {
                    "type": "number",
                    "description": "The second number to add.",
                },
            },
            "required": ["left", "right"]
        }
    )
    text_block_param: TextBlockParam = TextBlockParam(
        type="text",
        text="What's 2 + 3 and 10 + 20?"
    )
    message_param: MessageParam = MessageParam(
        role="user",
        content=[text_block_param]
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param],
        tools=[tool_param]
    )
    print(message.content)
    assert len(message.content) == 3

    block0: ContentBlock = message.content[0]
    assert block0.type == "text"

    block1: ContentBlock = message.content[1]
    assert type(block1) == ToolUseBlock
    assert block1.type == "tool_use"
    assert block1.name == "sum"

    block2: ContentBlock = message.content[2]
    assert type(block2) == ToolUseBlock
    assert block2.type == "tool_use"
    assert block2.name == "sum"
