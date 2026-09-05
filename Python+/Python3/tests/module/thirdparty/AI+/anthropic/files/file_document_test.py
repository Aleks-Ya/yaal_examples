from anthropic import Anthropic
from anthropic.types import Message, TextBlockParam, MessageParam, ModelParam, DocumentBlockParam, FileMetadata, \
    FileDocumentSourceParam
from anthropic.types.document_block_param import Source


def test_plain_text_file(client: Anthropic, model: ModelParam, max_tokens: int):
    document_content: str = "The magic number is 100."

    metadata: FileMetadata = client.files.upload(
        file=document_content.encode("utf-8"),
        expires_in_seconds=3600
    )

    source: Source = FileDocumentSourceParam(type="file", file_id=metadata.id)
    document_block_param: DocumentBlockParam = DocumentBlockParam(type="document", source=source)
    text_block_param: TextBlockParam = TextBlockParam(
        type="text", text="What's the magic number? Return just number.")
    message_param: MessageParam = MessageParam(role="user", content=[document_block_param, text_block_param])
    message: Message = client.messages.create(model=model, max_tokens=max_tokens, messages=[message_param])
    assert "100" in message.content[0].text

    client.files.delete(metadata.id)
