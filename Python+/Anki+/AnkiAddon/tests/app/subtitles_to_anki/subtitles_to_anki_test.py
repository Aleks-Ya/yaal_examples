from pathlib import Path

from app.subtitles_to_anki.anki_vocabulary import VocabularyWord, AnkiVocabulary
from app.subtitles_to_anki.sentences_to_words import SentenceWord
from app.subtitles_to_anki.subtitles_to_anki import SubtitlesToAnki

col_path: str = "/home/aleks/.local/share/Anki2/User 1/collection.anki2"


def test_subtitles_to_anki_dir():
    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    unknown_words_file: Path = srt_dir / "all_unknown.txt"
    unknown_sentence_words: list[SentenceWord] = SubtitlesToAnki.subtitles_to_anki_dir(col_path, srt_dir)
    print(f"Unknown sentence words count: {len(unknown_sentence_words)}")
    text: str = SubtitlesToAnki.format_words(unknown_sentence_words)
    unknown_words_file.write_text(text)


def test_subtitles_to_anki():
    srt: Path = Path.home() / "Downloads" / "The.Young.Pope.S01E01.English.Full.srt"
    __process(srt)


def test_subtitles_to_anki_episode_02():
    srt: Path = Path.home() / "Downloads" / "The.Young.Pope.S01E02.English.Full.srt"
    __process(srt)


def __process(srt):
    vocabulary_words: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)
    unknown_words_file: Path = srt.parent / f"{srt.stem}_unknown.txt"
    unknown_sentence_words: list[SentenceWord] = SubtitlesToAnki.find_unknown_words(srt, vocabulary_words)
    print(f"Unknown sentence words count: {len(unknown_sentence_words)}")
    text: str = SubtitlesToAnki.format_words(unknown_sentence_words)
    unknown_words_file.write_text(text)
