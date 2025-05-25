import dataclasses
from enum import Enum
from typing import Sequence, Optional

from anki.collection import Collection
from anki.notes import NoteId, Note


class POS(Enum):
    ADJECTIVE = "adjective"
    ADVERB = "adverb"
    CONJUNCTION = "conjunction"
    DETERMINER = "determiner"
    EXCLAMATION = "exclamation"
    INTERJECTION = "interjection"
    NOUN = "noun"
    NUMBER = "number"
    PREDETERMINER = "predeterminer"
    PREFIX = "prefix"
    PREPOSITION = "preposition"
    PRONOUN = "pronoun"
    VERB = "verb"

    def __repr__(self) -> str:
        return self.__str__()

    def __str__(self) -> str:
        return self.name.capitalize()

    @staticmethod
    def parse(pos: str) -> Optional['POS']:
        prefix: str = "en::parts::"
        if not pos.startswith(prefix):
            return None
        no_prefix_pos: str = pos.replace(prefix, "")
        no_suffix_pos: str = no_prefix_pos.split("::")[0]
        return POS[no_suffix_pos.upper()]

    @staticmethod
    def parse_list(pos_list: list[str]) -> Optional['POS']:
        parsed: list[Optional[POS]] = [POS.parse(pos) for pos in pos_list]
        non_null: list[POS] = [pos for pos in parsed if pos is not None]
        if len(non_null) > 1:
            raise ValueError(f"More than one POS found: {non_null}")
        if len(non_null) == 0:
            return None
        return non_null[0]


@dataclasses.dataclass
class Word:
    id: int
    word: str
    pos: Optional[POS]

    @staticmethod
    def parse(note: Note) -> 'Word':
        try:
            return Word(note.id, note["English"], POS.parse_list(note.tags))
        except Exception:
            raise ValueError(f"Can not parse {note.id}")


class AnkiVocabulary:
    @staticmethod
    def extract_known_vocabulary(collection_path: str) -> list[Word]:
        col: Collection = Collection(collection_path)
        note_ids: Sequence[NoteId] = col.find_notes("note:En-word-or-sentence")
        print(len(note_ids))
        notes: list[Note] = [col.get_note(note_id) for note_id in note_ids]
        words: list[Word] = [Word.parse(note) for note in notes]
        print(f"Words count: {len(words)}")
        words_pos: list[Word] = [word for word in words if word.pos is not None]
        print(f"Words with POS count: {len(words_pos)}")
        return words_pos
