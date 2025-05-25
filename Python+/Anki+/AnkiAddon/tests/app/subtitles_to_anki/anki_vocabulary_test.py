from app.subtitles_to_anki.anki_vocabulary import AnkiVocabulary, Word


def test_extract_known_vocabulary():
    col_path: str = "/home/aleks/.local/share/Anki2/User 1/collection.anki2"
    words_pos: list[Word] = AnkiVocabulary.extract_known_vocabulary(col_path)
    print(words_pos[0])
