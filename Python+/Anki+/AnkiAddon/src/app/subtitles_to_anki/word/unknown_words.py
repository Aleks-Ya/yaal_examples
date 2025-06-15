from pathlib import Path

from app.subtitles_to_anki.anki.anki_vocabulary import VocabularyWord, POS
from app.subtitles_to_anki.srt.sentences_to_words import SentencesToWords, SentenceWord
from app.subtitles_to_anki.word.word_formatter import WordFormatter


class UnknownWords:
    def __init__(self, vocabulary_words: list[VocabularyWord], out_dir: Path):
        self.__out_dir: Path = out_dir
        self.__vocabulary_words: list[VocabularyWord] = vocabulary_words

    def write_unknown_words(self, unknown_words: list[SentenceWord]) -> None:
        unknown_words_file: Path = self.__out_dir / "words_unknown.txt"
        unknown_words_text: str = WordFormatter.format_words(unknown_words)
        unknown_words_file.write_text(unknown_words_text)

    def filter_unknown_words(self, all_words: list[SentenceWord]) -> list[SentenceWord]:
        filtered_stop_words: list[SentenceWord] = SentencesToWords.filter_stop_words(all_words)
        unknown_words: list[SentenceWord] = self.__find_unknown_words(filtered_stop_words)
        print(f"Unknown words count: {len(unknown_words)}")
        return unknown_words

    def __find_unknown_words(self, sentence_words: list[SentenceWord]) -> list[SentenceWord]:
        unknown_words: list[SentenceWord] = []
        for sentence_word in sentence_words:
            lemma: str = sentence_word.lemma
            pos: POS = sentence_word.pos
            exists: bool = False
            for vocabulary_word in self.__vocabulary_words:
                if lemma.lower() in vocabulary_word.word.lower() and pos == vocabulary_word.pos:
                    exists = True
                    break
            if not exists:
                unknown_words.append(sentence_word)
        return unknown_words
