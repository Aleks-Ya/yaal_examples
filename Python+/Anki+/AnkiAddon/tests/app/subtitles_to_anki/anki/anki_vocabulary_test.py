from pathlib import Path

from app.subtitles_to_anki.anki.anki_vocabulary import AnkiVocabulary, VocabularyWord


def test_extract_known_vocabulary():
    col_path: Path = Path("/home/aleks/.local/share/Anki2/User 1/collection.anki2")
    words_pos: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)
    print(words_pos[0])
