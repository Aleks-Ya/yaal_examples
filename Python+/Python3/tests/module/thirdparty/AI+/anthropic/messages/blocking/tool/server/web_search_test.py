from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, WebSearchTool20250305Param, ServerToolUseBlock, \
    WebSearchToolResultBlock, TextBlock, ContentBlock


def test_web_search(client: Anthropic, model: str, max_tokens: int, remove_thinking_blocks, assert_blocks):
    web_search_tool_param: WebSearchTool20250305Param = WebSearchTool20250305Param(
        type="web_search_20250305",
        name="web_search"
    )
    text_block_param: TextBlockParam = TextBlockParam(
        type="text",
        text="What is the temperature in Bangkok now? Give just the number in Celsius."
    )
    message_param: MessageParam = MessageParam(
        role="user",
        content=[text_block_param]
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param],
        tools=[web_search_tool_param]
    )
    print(message.content)
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert_blocks(blocks, ServerToolUseBlock, WebSearchToolResultBlock, TextBlock)
