from anthropic import Anthropic
from anthropic.types import Message

client: Anthropic = Anthropic()
max_tokens: int = 5000
model: str = "claude-sonnet-5"
messages = []
print("Ctrl-D to exit.\n")
while True:
    try:
        user_message: str = input("> ")
    except EOFError:
        print("\nCancelled.")
        exit()
    messages.append({"role": "user", "content": user_message})
    message: Message = client.messages.create(messages=messages, model=model, max_tokens=max_tokens)
    print(message.content[0].text)
    messages.append({"role": "assistant", "content": message.content})
