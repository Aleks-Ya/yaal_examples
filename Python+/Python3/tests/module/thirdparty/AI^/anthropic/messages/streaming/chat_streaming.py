from pathlib import Path

from anthropic import Anthropic
from anthropic.types import MessageParam, ModelParam

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
    with client.messages.stream(messages=messages, model=model, max_tokens=max_tokens) as stream:
        print("-----")
        for text in stream.text_stream:
            print(text, end="", flush=True)
        print("\n-----")
        message = stream.get_final_message()
    messages.append(MessageParam(role="assistant", content=message.content))
