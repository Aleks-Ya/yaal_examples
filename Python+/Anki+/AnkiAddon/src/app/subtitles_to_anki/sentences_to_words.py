import dataclasses
import json
from itertools import groupby
from pathlib import Path
from typing import Any

from langdetect import detect
import spacy
from spacy import Language
from spacy.tokens import Doc
from spacy.symbols import PUNCT, SPACE, DET, CCONJ, ADP, SCONJ, NUM, PART, PRON, AUX, INTJ, X, SYM

from app.subtitles_to_anki.anki_vocabulary import POS


@dataclasses.dataclass(order=True)
class SentenceWord:
    lemma: str
    pos: POS
    sentences: list[str]

    def as_dict(self) -> dict[str, Any]:
        return {'lemma': self.lemma, 'pos': self.pos.name, 'sentences': self.sentences}

    @staticmethod
    def from_dict(d: dict[str, Any]) -> 'SentenceWord':
        return SentenceWord(d['lemma'], POS[d['pos']], d['sentences'])


class SentencesToWords:

    @staticmethod
    def sentences_to_words_with_part_of_speech(txt: Path) -> list[SentenceWord]:
        words_file: Path = txt.parent / f"{txt.stem}_words.json"
        if not words_file.exists():
            sentences: list[str] = txt.read_text().split('\n')
            print(f"Sentences count: {len(sentences)}")
            en_sentences: list[str] = [sentence for sentence in sentences if detect(sentence) == 'en']
            print(f"English sentences count: {len(en_sentences)}")
            unique_sentences: list[str] = list(dict.fromkeys(en_sentences))
            print(f"Unique English sentences count: {len(unique_sentences)}")
            words: list[SentenceWord] = SentencesToWords.__parse_words(unique_sentences)
            SentencesToWords.__save_to_file(words, words_file)
        else:
            print(f"Reading words from {words_file}")
            words: list[SentenceWord] = SentencesToWords.__read_from_file(words_file)
        return words

    @staticmethod
    def filter_stop_words(unique_words: list[SentenceWord]) -> list[SentenceWord]:
        stop_words_path: Path = Path(__file__).parent / "stop_words.txt"
        stop_words_by_line_break: list[str] = stop_words_path.read_text().split('\n')
        stop_words_by_comma: list[list[str]] = [word.split(",") for word in stop_words_by_line_break]
        stop_words_flatten: list[str] = [item.lower() for sublist in stop_words_by_comma for item in sublist]
        filtered_stop_words: list[SentenceWord] = [word for word in unique_words if
                                                   word.lemma.lower() not in stop_words_flatten]
        print(f"Filtered stop words count: {len(filtered_stop_words)}")
        return filtered_stop_words

    @staticmethod
    def __save_to_file(unique_words: list[SentenceWord], json_path: Path) -> None:
        with open(json_path, 'w', newline='') as fp:
            word_dict: list[dict[str, Any]] = [w.as_dict() for w in unique_words]
            json.dump(word_dict, fp, indent=2)

    @staticmethod
    def __read_from_file(json_path: Path) -> list[SentenceWord]:
        with open(json_path, 'r', newline='') as fp:
            word_dict: list[dict[str, Any]] = json.load(fp)
            word_list: list[SentenceWord] = [SentenceWord.from_dict(entry) for entry in word_dict]
            return word_list

    @staticmethod
    def find_unique_words(words: list[SentenceWord]) -> list[SentenceWord]:
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
                if token.pos not in [PUNCT, SPACE, DET, CCONJ, ADP, SCONJ, NUM, PART, PRON, AUX, INTJ, X, SYM]:
                    word: SentenceWord = SentenceWord(token.lemma_, POS.parse_spacy_pos(token.pos), [sentence_strip])
                    words.append(word)
        print(f"Words count: {len(words)}")
        return words
