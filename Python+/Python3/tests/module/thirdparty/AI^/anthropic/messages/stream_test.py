from anthropic import Anthropic


def test_create_message(client: Anthropic, model: str):
    with client.messages.stream(
            max_tokens=1024,
            messages=[{"role": "user", "content": "Who created the Java programming language?"}],
            model=model,
    ) as stream:
        for text in stream.text_stream:
            print(text, end="", flush=True)
