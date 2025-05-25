import dataclasses
import json
from itertools import groupby
from pathlib import Path
from typing import Any

from langdetect import detect
import spacy
from spacy import Language
from spacy.tokens import Doc
from spacy.symbols import PUNCT, SPACE, DET, CCONJ, ADP, SCONJ, NUM, PART, PRON, AUX, INTJ, X

from app.subtitles_to_anki.anki_vocabulary import POS


@dataclasses.dataclass(order=True)
class SentenceWord:
    lemma: str
    pos: POS
    sentences: list[str]

    def as_dict(self) -> dict[str, Any]:
        return {'lemma': self.lemma, 'pos': self.pos.name, 'sentences': self.sentences}


class SentencesToWords:

    @staticmethod
    def sentences_to_words_with_part_of_speech(txt: Path) -> list[SentenceWord]:
        sentences: list[str] = txt.read_text().split('\n')
        print(f"Sentences count: {len(sentences)}")
        en_sentences: list[str] = [sentence for sentence in sentences if detect(sentence) == 'en']
        print(f"English sentences count: {len(en_sentences)}")
        words: list[SentenceWord] = SentencesToWords.__parse_words(en_sentences)
        unique_words: list[SentenceWord] = SentencesToWords.__group_sentences(words)
        json_path: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.json"
        SentencesToWords.__save_to_file(unique_words, json_path)
        return unique_words

    @staticmethod
    def __save_to_file(unique_words: list[SentenceWord], json_path: Path) -> None:
        with open(json_path, 'w', newline='') as fp:
            word_dict: list[dict[str, Any]] = [w.as_dict() for w in unique_words]
            json.dump(word_dict, fp, indent=2)

    @staticmethod
    def __group_sentences(words: list[SentenceWord]) -> list[SentenceWord]:
        words.sort()
        grouped: groupby[Any, SentenceWord] = groupby(words, key=lambda w: w.lemma + "_" + w.pos.name)
        unique_words: list[SentenceWord] = []
        for group in grouped:
            elements: list[SentenceWord] = list(group[1])
            sentences: list[str] = [w.sentences[0] for w in elements]
            word: SentenceWord = SentenceWord(elements[0].lemma, elements[0].pos, sentences)
            unique_words.append(word)
        sorted_words: list[SentenceWord] = sorted(unique_words, key=lambda w: len(w.sentences), reverse=True)
        print(f"Unique lemmas count: {len(sorted_words)}")
        return sorted_words

    @staticmethod
    def __parse_words(sentences: list[str]) -> list[SentenceWord]:
        # Download data first: python -m spacy download en_core_web_sm
        nlp: Language = spacy.load("en_core_web_sm")
        words: list[SentenceWord] = []
        for sentence in sentences:
            sentence_strip: str = sentence.strip()
            doc: Doc = nlp(sentence_strip)
            for token in doc:
                if token.pos not in [PUNCT, SPACE, DET, CCONJ, ADP, SCONJ, NUM, PART, PRON, AUX, INTJ, X]:
                    word: SentenceWord = SentenceWord(token.lemma_, POS.parse_spacy_pos(token.pos), [sentence_strip])
                    words.append(word)
        print(f"Words count: {len(words)}")
        return words
