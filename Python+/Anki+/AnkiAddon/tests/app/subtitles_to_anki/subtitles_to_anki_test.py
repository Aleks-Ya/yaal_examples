from pathlib import Path

from app.subtitles_to_anki.sentences_to_words import SentenceWord
from app.subtitles_to_anki.subtitles_to_anki import SubtitlesToAnki

col_path: str = "/home/aleks/.local/share/Anki2/User 1/collection.anki2"


def test_extract_all_words():
    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    all_words_file: Path = srt_dir / "words_all.txt"
    all_words: list[SentenceWord] = SubtitlesToAnki.extract_all_words(srt_dir)
    print(f"All words count: {len(all_words)}")
    text: str = SubtitlesToAnki.format_words(all_words)
    all_words_file.write_text(text)


def test_filter_unknown_worlds():
    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    unknown_words_file: Path = srt_dir / "words_unknown.txt"
    all_words: list[SentenceWord] = SubtitlesToAnki.extract_all_words(srt_dir)
    unknown_words: list[SentenceWord] = SubtitlesToAnki.filter_unknown_words(all_words, col_path)
    print(f"Unknown words count: {len(unknown_words)}")
    text: str = SubtitlesToAnki.format_words(unknown_words)
    unknown_words_file.write_text(text)
