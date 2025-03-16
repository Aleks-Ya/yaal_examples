import logging
from typing import Optional

log: logging.Logger = logging.getLogger(__name__)


class LanguageAiConfig:

    def __init__(self, config: Optional[dict[str, any]]):
        self.config: Optional[dict[str, any]] = config

    def get_openai_client_config(self) -> dict[str, any]:
        return self.config['openai-client']

    def get_synonym_headers(self) -> list[str]:
        synonym_number: int = self.config['synonyms']['synonym_number']
        synonym_headers = [f'Synonym {i}' for i in range(1, synonym_number + 1)]
        log.info(f"synonym_headers: {synonym_headers}")
        return synonym_headers

    def get_antonym_headers(self) -> list[str]:
        antonym_number: int = self.config['antonyms']['antonym_number']
        antonym_headers = [f'Antonym {i}' for i in range(1, antonym_number + 1)]
        log.info(f"antonym_headers: {antonym_headers}")
        return antonym_headers

    def get_synonyms_delimiter(self) -> str:
        return self.config['synonyms']['delimiter']

    def get_antonyms_delimiter(self) -> str:
        return self.config['antonyms']['delimiter']
