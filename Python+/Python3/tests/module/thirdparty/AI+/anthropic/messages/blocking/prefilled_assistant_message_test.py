import json

from anthropic import Anthropic
from anthropic.types import Message


# Prefilled assistant message is deprecated

def test_use_prefilled_assistant_message(client: Anthropic, max_tokens: int):
    message: Message = client.messages.create(
        model='claude-haiku-4-5',  # Prefilled assistant message is deprecated in new models
        max_tokens=max_tokens,
        messages=[
            {
                "role": "user",
                "content": "Generate a JSON document with a list of 3 fruits and their colors.",
            },
            {
                "role": "assistant",
                "content": "```json",
            }
        ],
        stop_sequences=["```"]
    )
    md_str: str = message.model_dump_json()  # TODO already returns a JSON string, not Markdown
    print(md_str)
    json_str: str = md_str.replace("```json", "").replace("```", "")
    json.loads(json_str)
