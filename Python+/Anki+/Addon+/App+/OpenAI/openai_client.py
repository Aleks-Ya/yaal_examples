import logging
from pathlib import Path

from openai import OpenAI
from openai.types.chat import ChatCompletion

log: logging.Logger = logging.getLogger(__name__)


def _init_openai_client() -> OpenAI:
    key_file: Path = Path.home().joinpath('.gpt/token.txt')
    with open(key_file) as f:
        key: str = f.read()
    return OpenAI(api_key=key)


def get_completion(prompt: str, timeout_sec: float = 120):
    log.info(f"Request timeout: {timeout_sec} sec")
    client: OpenAI = _init_openai_client()
    chat_completion: ChatCompletion = client.chat.completions.create(
        messages=[
            {
                "role": "user",
                "content": prompt,
            }
        ],
        model="gpt-4-1106-preview",
        timeout=timeout_sec
    )
    return chat_completion
