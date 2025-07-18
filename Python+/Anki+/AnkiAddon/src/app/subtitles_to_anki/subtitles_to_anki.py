from pathlib import Path

from app.subtitles_to_anki.anki.anki_vocabulary import AnkiVocabulary, VocabularyWord
from app.subtitles_to_anki.srt.sentences_to_words import SentenceWord
from app.subtitles_to_anki.word.unknown_words import UnknownWords
from app.subtitles_to_anki.word.all_words import AllWords

if __name__ == "__main__":
    print("Start")

    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    out_dir: Path = srt_dir / "out"

    col_path: Path = Path.home() / ".local" / "share" / "Anki2" / "User 1" / "collection.anki2"
    vocabulary_words: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)
    unknown_words: UnknownWords = UnknownWords(vocabulary_words, out_dir)

    all_words: AllWords = AllWords(out_dir, unknown_words)
    all_words_list: list[SentenceWord] = all_words.extract_words(srt_dir)

    unknown_word_list: list[SentenceWord] = unknown_words.filter_unknown_words(all_words_list)
    unknown_words.write_unknown_words(unknown_word_list)

    print("Done")
