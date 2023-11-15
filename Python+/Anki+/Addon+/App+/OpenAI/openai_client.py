from pathlib import Path

from openai import OpenAI


def init_openai_client() -> OpenAI:
    key_file: Path = Path.home().joinpath('.gpt/token.txt')
    with open(key_file) as f:
        key: str = f.read()
    return OpenAI(api_key=key)
