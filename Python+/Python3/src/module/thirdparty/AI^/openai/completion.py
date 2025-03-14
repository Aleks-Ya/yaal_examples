from pathlib import Path

from openai import OpenAI
from openai.types.chat import ChatCompletion

key_file: Path = Path.home().joinpath('.gpt/token.txt')
with open(key_file) as f:
    key: str = f.read()

client: OpenAI = OpenAI(api_key=key)

chat_completion: ChatCompletion = client.chat.completions.create(
    messages=[
        {
            "role": "user",
            "content": "Say this is a test",
        }
    ],
    model="gpt-4-1106-preview",
)

print(chat_completion)
