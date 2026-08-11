from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ServerToolUseBlock, \
    TextBlock, CodeExecutionTool20250522Param, CodeExecutionToolResultBlock, ContentBlock


def test_code_execution(client: Anthropic, model: str, max_tokens: int, remove_thinking_blocks, assert_blocks):
    code_execution_tool_param: CodeExecutionTool20250522Param = CodeExecutionTool20250522Param(
        type="code_execution_20250522",
        name="code_execution"
    )
    text_block_param: TextBlockParam = TextBlockParam(
        type="text",
        text="Create a Python function that calculates MD5 of given string. "
             "Execute this function with string `abc`. "
             "Return just the MD5 hash."
    )
    message_param: MessageParam = MessageParam(
        role="user",
        content=[text_block_param]
    )
    message: Message = client.messages.create(
        model=model,
        max_tokens=max_tokens,
        messages=[message_param],
        tools=[code_execution_tool_param]
    )
    print(message.content)
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert_blocks(blocks, ServerToolUseBlock, CodeExecutionToolResultBlock, TextBlock)
    assert "900150983cd24fb0d6963f7d28e17f72" in blocks[2].text
