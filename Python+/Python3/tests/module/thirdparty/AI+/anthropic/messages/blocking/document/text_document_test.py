from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ModelParam, DocumentBlockParam, PlainTextSourceParam
from anthropic.types.document_block_param import Source


def test_plain_text_source(client: Anthropic, model: ModelParam, max_tokens: int):
    document_content: str = """
    # Financial Report

    ## Expectations
    Very high.
    
    ## Results
    Even higher.
    """
    source: Source = PlainTextSourceParam(type="text", media_type="text/plain", data=document_content)
    document_block_param: DocumentBlockParam = DocumentBlockParam(type="document", source=source)
    text_block_param: TextBlockParam = TextBlockParam(
        type="text", text="Are the results satisfactory? Answer `yes` or `no`.")
    message_param: MessageParam = MessageParam(role="user", content=[document_block_param, text_block_param])
    message: Message = client.messages.create(model=model, max_tokens=max_tokens, messages=[message_param])
    assert message.content[0].text.lower() == "yes"


def test_two_documents(client: Anthropic, model: ModelParam, max_tokens: int):
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
    document_block_param_1: DocumentBlockParam = DocumentBlockParam(type="document", source=source_1)
    document_block_param_2: DocumentBlockParam = DocumentBlockParam(type="document", source=source_2)
    text_block_param: TextBlockParam = TextBlockParam(
        type="text", text="What's the population of Delhi? Return just number.")
    message_param: MessageParam = MessageParam(
        role="user", content=[document_block_param_1, document_block_param_2, text_block_param])
    message: Message = client.messages.create(model=model, max_tokens=max_tokens, messages=[message_param])
    assert "310" in message.content[0].text
