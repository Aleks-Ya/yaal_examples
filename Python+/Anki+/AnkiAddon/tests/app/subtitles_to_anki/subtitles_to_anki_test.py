from pathlib import Path

from app.subtitles_to_anki.sentences_to_words import SentenceWord
from app.subtitles_to_anki.subtitles_to_anki import SubtitlesToAnki

col_path: str = "/home/aleks/.local/share/Anki2/User 1/collection.anki2"


def test_subtitles_to_anki():
    srt: Path = Path.home() / "Downloads" / "The.Young.Pope.S01E01.English.Full.srt"
    __process(srt)


def test_subtitles_to_anki_episode_02():
    srt: Path = Path.home() / "Downloads" / "The.Young.Pope.S01E02.English.Full.srt"
    __process(srt)


def __process(srt):
    unknown_words_file: Path = srt.parent / f"{srt.stem}_unknown.txt"
    unknown_sentence_words: list[SentenceWord] = SubtitlesToAnki.subtitles_to_anki(col_path, srt)
    print(f"Unknown sentence words count: {len(unknown_sentence_words)}")
    text: str = SubtitlesToAnki.format_words(unknown_sentence_words)
    unknown_words_file.write_text(text)
