from anthropic import Anthropic
from anthropic.types import Message, ToolParam, ContentBlock, TextBlock, MessageParam, TextBlockParam, \
    ToolUseBlockParam, ToolResultBlockParam, ModelParam


def test_tool_result_block_with_error(client: Anthropic, model: ModelParam, max_tokens: int):
    tool_param: ToolParam = ToolParam(
        name="get_magic_number",
        description="Get the magic number.",
        input_schema={"type": "object"}
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[
            MessageParam(
                role="user",
                content=[TextBlockParam(
                    type="text",
                    text="What's the magic number? Print only the number."
                )]
            ),
            MessageParam(
                role="assistant",
                content=[ToolUseBlockParam(
                    type="tool_use",
                    id="toolu_01ABC",
                    name="get_magic_number",
                    input={}
                )]
            ),
            MessageParam(
                role="user",
                content=[ToolResultBlockParam(
                    type="tool_result",
                    tool_use_id="toolu_01ABC",
                    content="Magic does not work today",
                    is_error=True
                )]
            )
        ],
        tools=[tool_param]
    )
    print(message)
    assert len(message.content) == 1

    block0: ContentBlock = message.content[0]
    assert type(block0) == TextBlock
    assert block0.type == "text"
    assert "error" in block0.text
