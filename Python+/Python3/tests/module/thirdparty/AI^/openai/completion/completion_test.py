from openai import OpenAI
from openai.types.chat import ChatCompletion, ChatCompletionMessageParam, ChatCompletionUserMessageParam


def test_completion_json(client: OpenAI):
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


def test_completion(client: OpenAI):
    messages: list[ChatCompletionMessageParam] = [
        ChatCompletionUserMessageParam(content="Say this is a test", role="user")
    ]
    chat_completion: ChatCompletion = client.chat.completions.create(messages=messages, model="gpt-4.1")
    print(chat_completion)
