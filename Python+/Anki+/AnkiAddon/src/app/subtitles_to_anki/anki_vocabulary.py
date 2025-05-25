import dataclasses
from enum import Enum
from functools import total_ordering
from typing import Sequence, Optional

from anki.collection import Collection
from anki.notes import NoteId, Note
from spacy.symbols import PUNCT, SPACE, DET, CCONJ, ADP, SCONJ, NUM, PART, PRON, AUX, INTJ, ADJ, NOUN, PROPN, VERB, ADV


@total_ordering
class POS(Enum):
    ADJECTIVE = "adjective"
    ADVERB = "adverb"
    CONJUNCTION = "conjunction"
    DETERMINER = "determiner"
    EXCLAMATION = "exclamation"
    INTERJECTION = "interjection"
    NOUN = "noun"
    PRONOUN = "pronoun"
    PROPER_NOUN = "proper noun"
    NUMBER = "number"
    PREDETERMINER = "predeterminer"
    PREFIX = "prefix"
    PREPOSITION = "preposition"
    VERB = "verb"

    def __repr__(self) -> str:
        return self.__str__()

    def __str__(self) -> str:
        return self.name.capitalize()

    def __lt__(self, other):
        if not isinstance(other, POS):
            return NotImplemented
        return self.name < other.name

    @staticmethod
    def parse(pos: str) -> Optional['POS']:
        prefix: str = "en::parts::"
        if not pos.startswith(prefix):
            return None
        no_prefix_pos: str = pos.replace(prefix, "")
        split: list[str] = no_prefix_pos.split("::")
        no_suffix_pos: str = split[0]
        pos: POS = POS[no_suffix_pos.upper()]
        if pos == NOUN:
            proper_suffix: str = split[1]
            if proper_suffix == "proper":
                return POS.PROPER_NOUN
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

    @staticmethod
    def parse_spacy_pos(pos: int) -> 'POS':
        mapping: dict[int, POS] = {
            PUNCT: POS.EXCLAMATION,
            SPACE: POS.PREFIX,
            DET: POS.DETERMINER,
            CCONJ: POS.CONJUNCTION,
            ADP: POS.PREPOSITION,
            SCONJ: POS.PREDETERMINER,
            NUM: POS.NUMBER,
            PART: POS.PREFIX,
            PRON: POS.PRONOUN,
            PROPN: POS.PROPER_NOUN,
            AUX: POS.VERB,
            INTJ: POS.INTERJECTION,
            ADJ: POS.ADJECTIVE,
            NOUN: POS.NOUN,
            VERB: POS.VERB,
            ADV: POS.ADVERB,
        }
        return mapping[pos]


@dataclasses.dataclass
class VocabularyWord:
    word: str
    pos: Optional[POS]

    @staticmethod
    def parse(note: Note) -> 'VocabularyWord':
        try:
            return VocabularyWord(note["English"], POS.parse_list(note.tags))
        except Exception:
            raise ValueError(f"Can not parse {note.id}")


class AnkiVocabulary:
    @staticmethod
    def extract_known_vocabulary(collection_path: str) -> list[VocabularyWord]:
        col: Collection = Collection(collection_path)
        note_ids: Sequence[NoteId] = col.find_notes("note:En-word-or-sentence")
        print(len(note_ids))
        notes: list[Note] = [col.get_note(note_id) for note_id in note_ids]
        words: list[VocabularyWord] = [VocabularyWord.parse(note) for note in notes]
        print(f"Words count: {len(words)}")
        words_pos: list[VocabularyWord] = [word for word in words if word.pos is not None]
        print(f"Words with POS count: {len(words_pos)}")
        return words_pos
