import dataclasses
import json
from itertools import groupby
from pathlib import Path
from typing import Any

import spacy
from spacy import Language
from spacy.tokens import Doc


@dataclasses.dataclass(order=True)
class Word:
    lemma: str
    pos: str
    sentences: list[str]

class SentencesToWords:

    @staticmethod
    def sentences_to_words_with_part_of_speech( txt: Path, json_path: Path) -> None:
        sentences: list[str] = txt.read_text().split('\n')
        words: list[Word] = SentencesToWords.__parse_words(sentences)
        unique_words: list[Word] = SentencesToWords.__group_sentences(words)
        SentencesToWords.__save_to_file(unique_words, json_path)

    @staticmethod
    def __save_to_file(unique_words: list[Word], json_path: Path) -> None:
        with open(json_path, 'w', newline='') as fp:
            word_dict: list[dict[str, Any]] = [dataclasses.asdict(w) for w in unique_words]
            json.dump(word_dict, fp, indent=2)

    @staticmethod
    def __group_sentences(words: list[Word]) -> list[Word]:
        words.sort()
        grouped: groupby[Any, Word] = groupby(words, key=lambda w: w.lemma + "_" + w.pos)
        unique_words: list[Word] = []
        for group in grouped:
            elements: list[Word] = list(group[1])
            sentences: list[str] = [w.sentences[0] for w in elements]
            word: Word = Word(elements[0].lemma, elements[0].pos, sentences)
            unique_words.append(word)
        sorted_words: list[Word] = sorted(unique_words, key=lambda w: len(w.sentences), reverse=True)
        print(f"Unique lemmas count: {len(sorted_words)}")
        return sorted_words

    @staticmethod
    def __parse_words(sentences: list[str]) -> list[Word]:
        # Download data first: python -m spacy download en_core_web_sm
        nlp: Language = spacy.load("en_core_web_sm")
        words: list[Word] = []
        for sentence in sentences:
            sentence_strip: str = sentence.strip()
            doc: Doc = nlp(sentence_strip)
            for token in doc:
                if token.pos_ not in ['PUNCT', 'SPACE', 'DET', 'CCONJ', 'ADP', 'SCONJ', 'NUM', 'PART', 'PRON', 'AUX',
                                      'INTJ']:
                    word: Word = Word(token.lemma_, token.pos_, [sentence_strip])
                    words.append(word)
        print(f"Words count: {len(words)}")
        return words
