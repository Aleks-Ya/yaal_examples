import logging
from pathlib import Path
from typing import Optional, Any

from openai import OpenAI
from openai.types.chat import ChatCompletion

log: logging.Logger = logging.getLogger(__name__)


class OpenAiClient:

    def __init__(self, config: Optional[dict[str, Any]]):
        key_file: Path = Path.home().joinpath('.gpt/token.txt')
        with open(key_file) as f:
            key: str = f.read()
        self.client = OpenAI(api_key=key)
        self.model = config['model']
        log.info(f"Model: {self.model}")

    def get_completion(self, prompt: str, timeout_sec: float = 120):
        log.info(f"Request timeout: {timeout_sec} sec")
        chat_completion: ChatCompletion = self.client.chat.completions.create(
            messages=[
                {
                    "role": "user",
                    "content": prompt,
                }
            ],
            model=self.model,
            timeout=timeout_sec
        )
        return chat_completion
