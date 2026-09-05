from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ModelParam, DocumentBlockParam, PlainTextSourceParam, \
    CitationsConfigParam, ContentBlock
from anthropic.types.document_block_param import Source


def test_citations(client: Anthropic, model: ModelParam, max_tokens: int, remove_thinking_blocks):
    document_content_1: str = """
    # Car prices
    BMW: $500,000
    Audi: $450,000
    Volkswagen: $300,000
    """
    document_content_2: str = """
    # Largest cities in the world
    Tokyo: 370 million
    Delhi: 310 million
    Shanghai: 270 million
    """
    source_1: Source = PlainTextSourceParam(type="text", media_type="text/plain", data=document_content_1)
    source_2: Source = PlainTextSourceParam(type="text", media_type="text/plain", data=document_content_2)
    document_block_param_1: DocumentBlockParam = DocumentBlockParam(
        type="document", title="Car info", source=source_1, citations=CitationsConfigParam(enabled=True))
    document_block_param_2: DocumentBlockParam = DocumentBlockParam(
        type="document", title="Cities info", source=source_2, citations=CitationsConfigParam(enabled=True))
    text_block_param: TextBlockParam = TextBlockParam(
        type="text", text="What's the population of Delhi? Return just number.")
    message_param: MessageParam = MessageParam(
        role="user", content=[document_block_param_1, document_block_param_2, text_block_param])
    message: Message = client.messages.create(model=model, max_tokens=max_tokens, messages=[message_param])
    blocks: list[ContentBlock] = remove_thinking_blocks(message.content)
    assert "310" in blocks[0].text
    assert blocks[0].citations is not None
    assert blocks[0].citations[0].document_index == 1
    assert blocks[0].citations[0].document_title == "Cities info"
