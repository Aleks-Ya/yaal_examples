import logging
from typing import List, Optional, Any

from anki.notes import Note
from openai.types.chat import ChatCompletion

from common.fields import english_field
from common.part_of_speech import PartOfSpeech
from openai_client.openai_client import OpenAiClient
from .columns import nid_column, english_column, synonym_headers, antonym_headers
from .columns import pos_column

log: logging.Logger = logging.getLogger(__name__)


class OpenAiApi:
    def __init__(self, config: Optional[dict[str, Any]]):
        self.openai_client: OpenAiClient = OpenAiClient(config)
        self.part_of_speech = PartOfSpeech()

    def request_answer(self, notes: List[Note]) -> str:
        lines: List[str] = []
        for note in notes:
            try:
                word: str = note[english_field]
                pos: str = self.part_of_speech.tagsToSinglePos(note.tags)
                line: str = f'"{note.id}","{word}","{pos}"'
                lines.append(line)
            except Exception as e:
                raise Exception(f"Failed to process note: {note.id}") from e
        lines_str: str = '\n'.join(lines)
        synonym_headers_quoted: List[str] = [f'"{synonym_header}"' for synonym_header in synonym_headers]
        antonym_headers_quoted: List[str] = [f'"{antonym_header}"' for antonym_header in antonym_headers]
        log.debug(f"synonym_headers_quoted: {synonym_headers_quoted}")
        log.debug(f"antonym_headers_quoted: {synonym_headers_quoted}")
        all_headers_quoted: List[str] = list(synonym_headers_quoted)
        all_headers_quoted += antonym_headers_quoted
        prompt: str = (
            f'I will provide you a CSV snippet. '
            f'You need to fill columns {", ".join(all_headers_quoted)} in the snippet. '
            f'Use the most simple and wide-spread synonyms/antonyms if possible. '
            f'One-word synonyms/antonyms are preferred over multi-words. '
            f'If synonyms/antonyms absents, leave the column empty. '
            f'Your response must contain strictly only raw CSV content (it is very important). '
            f'The CSV snippet is:\n'
            f'```\n'
            f'"{nid_column}","{english_column}","{pos_column}",{",".join(all_headers_quoted)}\n'
            f'{lines_str}\n'
            f'```\n'
        )
        log.debug(f"Prompt:\n{prompt}")
        chat_completion: ChatCompletion = self.openai_client.get_completion(prompt)
        message: str = chat_completion.choices[0].message.content
        log.debug(f"Message:\n{message}")
        message = message.replace('```\n', '').replace('\n```', '')
        log.debug(f"Message without MarkDown:\n{message}")
        return message
