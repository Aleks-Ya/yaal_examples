from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, \
    WebFetchTool20250910Param, ServerToolUseBlock, WebFetchToolResultBlock, TextBlock, ContentBlock


def test_web_fetch(client: Anthropic, model: str, max_tokens: int, remove_thinking_blocks):
    web_fetch_tool_param: WebFetchTool20250910Param = WebFetchTool20250910Param(
        type="web_fetch_20250910",
        name="web_fetch"
    )
    text_block_param: TextBlockParam = TextBlockParam(
        type="text",
        text="Return just the content at https://httpbin.io/base64/Z3JlZW4= URL."
    )
    message_param: MessageParam = MessageParam(
        role="user",
        content=[text_block_param]
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param],
        tools=[web_fetch_tool_param]
    )
    print(message.content)
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert len(blocks) == 3

    assert type(blocks[0]) == ServerToolUseBlock
    assert type(blocks[1]) == WebFetchToolResultBlock
    assert type(blocks[2]) == TextBlock

    assert blocks[2].text == "green"
