from anthropic import Anthropic
from anthropic.types import Message, ToolParam, ContentBlock, ToolUseBlock, TextBlockParam, MessageParam, ModelParam, \
    TextBlock


def test_single_tool_use_block(client: Anthropic, model: str, max_tokens: int, remove_thinking_blocks, assert_blocks):
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
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert_blocks(blocks, ToolUseBlock)
    assert len(message.content) == 1

    assert type(blocks[0]) == ToolUseBlock
    assert blocks[0].type == "tool_use"
    assert blocks[0].name == "get_magic_number"


def test_multiple_tool_use_blocks(client: Anthropic, model: ModelParam, max_tokens: int, remove_thinking_blocks,
                                  assert_blocks):
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
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert_blocks(blocks, TextBlock, ToolUseBlock, ToolUseBlock)

    assert blocks[0].type == "text"

    assert blocks[1].type == "tool_use"
    assert blocks[1].name == "sum"

    assert blocks[2].type == "tool_use"
    assert blocks[2].name == "sum"
