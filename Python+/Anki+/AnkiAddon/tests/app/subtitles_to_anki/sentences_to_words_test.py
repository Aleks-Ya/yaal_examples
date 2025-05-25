from pathlib import Path

from app.subtitles_to_anki.sentences_to_words import SentencesToWords


def test_sentences_to_words_with_part_of_speech():
    txt: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.txt"
    json_path: Path = Path.home() / "Downloads/The.Young.Pope.S01E01.English.Full.json"
    SentencesToWords.sentences_to_words_with_part_of_speech(txt, json_path)
