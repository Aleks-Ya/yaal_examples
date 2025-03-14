from pathlib import Path

from openai import OpenAI
from openai.types import FileObject

key_file: Path = Path.home().joinpath('.gpt/token.txt')
with open(key_file) as f:
    key: str = f.read()

client: OpenAI = OpenAI(api_key=key)

# DOES NOT WORK
uploaded_file: FileObject = client.files.create(
    file=Path("data.json"),
    purpose="assistants",
)

print(uploaded_file)
