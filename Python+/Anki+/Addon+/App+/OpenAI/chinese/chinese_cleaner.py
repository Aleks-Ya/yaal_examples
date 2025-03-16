# DO NOT USE: works slow, GPT responses are unreliable
import logging
import re
from typing import List

from anki.notes import NoteId, Note
from openai.types.chat import ChatCompletion

from common.config import LanguageAiConfig
from common.fields import examples1_generated_field, \
    examples2_generated_field, examples3_generated_field
from openai_client.openai_client import OpenAiClient

log: logging.Logger = logging.getLogger(__name__)

fields: List[str] = [examples1_generated_field, examples2_generated_field, examples3_generated_field]


class ChineseCleaner:
    def __init__(self, config: LanguageAiConfig):
        self.openai_client: OpenAiClient = OpenAiClient(config)

    @staticmethod
    def __contains_chinese(text: str) -> bool:
        pattern = r'[\u4e00-\u9fff\u3400-\u4dbf\u2e80-\u2eff\u3000-\u303f\uff00-\uffef]'
        if re.search(pattern, text):
            return True
        return False

    def note_contains_chinese(self, note: Note) -> bool:
        for field in fields:
            if self.__contains_chinese(note[field]):
                return True
        return False

    def clean_field(self, old_value: str, english: str, field: str, note_id: NoteId) -> str:
        if not self.__contains_chinese(old_value):
            log.info(f"Skip field without Chinese characters: nid={note_id}, field={field}")
            return old_value
        prompt: str = (
            f'I will give you an HTML snippet.\n'
            f'\n'
            f'You need to perform these operations on the snippet:\n'
            f'1. Remove any information related to CSS.\n'
            f'2. Remove Chinese, Japanese or Korean characters.\n'
            f'3. Remove tags that became empty.\n'
            f'4. Convert it to a flat HTML list without `div` tags.\n'
            f'5. If any list element starts with number duplicating `li` tag, remove the number.\n'
            f'6. Surround word `{english}` with `em` tag if it is not surrounded already.\n'
            f'\n'
            f'Your response must contain strictly only resulting snippet without any comments (it is very important).\n'
            f'The text is:\n'
            f'```\n'
            f'{old_value}\n'
            f'```'
        )
        log.debug(f"Prompt:\n{prompt}")
        chat_completion: ChatCompletion = self.openai_client.get_completion(prompt)
        message: str = chat_completion.choices[0].message.content
        log.debug(f"Message:\n{message}")
        clean_value: str = message.replace('```\n', '').replace('```html\n', '').replace('\n```', '')
        log.debug(f"Message without MarkDown:\n{clean_value}")
        if self.__contains_chinese(clean_value):
            raise RuntimeError(f'Cleaned text still contains Chinese characters: nid={note_id}, text="{clean_value}"')
        return clean_value
