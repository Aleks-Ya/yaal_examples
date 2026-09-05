import base64
from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ModelParam, DocumentBlockParam, Base64PDFSourceParam
from anthropic.types.document_block_param import Source

from yaal_helpers.current_path import get_file_in_current_dir


def test_base64_pdf_source(client: Anthropic, model: ModelParam, max_tokens: int):
    document_bytes: bytes = get_file_in_current_dir("Wakeboarding.pdf").read_bytes()
    document_data: str = base64.standard_b64encode(document_bytes).decode("utf-8")
    source: Source = Base64PDFSourceParam(type="base64", media_type="application/pdf", data=document_data)
    document_block_param: DocumentBlockParam = DocumentBlockParam(type="document", source=source)
    text_block_param: TextBlockParam = TextBlockParam(
        type="text", text="What this document about? Answer in one word.")
    message_param: MessageParam = MessageParam(role="user", content=[document_block_param, text_block_param])
    message: Message = client.messages.create(model=model, max_tokens=max_tokens, messages=[message_param])
    assert "wakeboarding" in message.content[0].text.lower()
