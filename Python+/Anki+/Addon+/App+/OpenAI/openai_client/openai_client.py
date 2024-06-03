import logging
import os.path
from typing import Any

from openai import OpenAI
from openai.types.chat import ChatCompletion

from common.config import LanguageAiConfig

log: logging.Logger = logging.getLogger(__name__)


class OpenAiClient:

    def __init__(self, config: LanguageAiConfig):
        openai_config: dict[str, Any] = config.get_openai_client_config()
        token_path: str = openai_config['token-path']
        key_file: str = os.path.expandvars(token_path)
        with open(key_file) as f:
            key: str = f.read()
        self.client: OpenAI = OpenAI(api_key=key)
        self.model: str = openai_config['model']
        self.timeout_sec: int = openai_config['timeout_sec']
        log.info(f"Model: {self.model}")
        log.info(f"Request timeout: {self.timeout_sec} sec")

    def get_completion(self, prompt: str):
        chat_completion: ChatCompletion = self.client.chat.completions.create(
            messages=[
                {
                    "role": "user",
                    "content": prompt,
                }
            ],
            model=self.model,
            timeout=self.timeout_sec
        )
        return chat_completion
