from anthropic import Anthropic

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
    with client.messages.stream(messages=messages, model=model, max_tokens=max_tokens) as stream:
        print("-----")
        for text in stream.text_stream:
            print(text, end="", flush=True)
        print("\n-----")
        message = stream.get_final_message()
    messages.append({"role": "assistant", "content": message.content})
