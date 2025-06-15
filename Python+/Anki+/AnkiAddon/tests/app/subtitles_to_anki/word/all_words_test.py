import tempfile
from pathlib import Path

from app.subtitles_to_anki.anki.anki_vocabulary import VocabularyWord, AnkiVocabulary
from app.subtitles_to_anki.srt.sentences_to_words import SentenceWord
from app.subtitles_to_anki.word.unknown_words import UnknownWords
from app.subtitles_to_anki.word.word_formatter import WordFormatter
from app.subtitles_to_anki.word.all_words import AllWords

col_path: Path = Path("/home/aleks/.local/share/Anki2/User 1/collection.anki2")


def test_extract_all_words():
    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    all_words_file: Path = srt_dir / "words_all.txt"
    out_dir: Path = Path(tempfile.mkdtemp())
    vocabulary_words: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)
    unknown_words: UnknownWords = UnknownWords(vocabulary_words, out_dir)
    all_words: AllWords = AllWords(out_dir, unknown_words)
    all_words_list: list[SentenceWord] = all_words.extract_words(srt_dir)
    print(f"All words count: {len(all_words_list)}")
    text: str = WordFormatter.format_words(all_words_list)
    all_words_file.write_text(text)
