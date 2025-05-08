from openai import OpenAI
from openai.types.chat import ChatCompletion


def test_completion(client: OpenAI):
    chat_completion: ChatCompletion = client.chat.completions.create(
        messages=[
            {
                "role": "user",
                "content": "Say this is a test",
            }
        ],
        model="gpt-4.1",
    )
    print(chat_completion)
