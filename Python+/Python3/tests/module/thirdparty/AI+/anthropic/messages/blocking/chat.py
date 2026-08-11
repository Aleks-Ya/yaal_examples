from pathlib import Path

from anthropic import Anthropic
from anthropic.types import Message, MessageParam, ModelParam

key_file: Path = Path.home() / ".config" / "anthropic-api-key" / "personal-anthropic-api-key.txt"
key: str = key_file.read_text().strip()
client: Anthropic = Anthropic(api_key=key)
max_tokens: int = 5000
model: ModelParam = "claude-sonnet-5"
messages: list[MessageParam] = []
print("Ctrl-D to exit.\n")
while True:
    try:
        user_message: str = input("> ")
    except EOFError:
        print("\nCancelled.")
        exit()
    messages.append(MessageParam(role="user", content=user_message))
    message: Message = client.messages.create(messages=messages, model=model, max_tokens=max_tokens)
    for content in message.content:
        if content.type == "text":
            print(content.text)
    messages.append(MessageParam(role="assistant", content=message.content))
