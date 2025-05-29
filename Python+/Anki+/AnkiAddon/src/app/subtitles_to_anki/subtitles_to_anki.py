from pathlib import Path

from app.subtitles_to_anki.anki_vocabulary import VocabularyWord, AnkiVocabulary, POS
from app.subtitles_to_anki.sentences_to_words import SentencesToWords, SentenceWord
from app.subtitles_to_anki.srt_to_txt import SrtToTxt


class SubtitlesToAnki:

    @staticmethod
    def extract_all_words(srt_dir: Path) -> list[SentenceWord]:
        srt_files: list[Path] = list(srt_dir.glob("*.srt"))
        srt_files_str: str = "\n".join([f"{srt}" for srt in srt_files])
        print(f"\nFound {len(srt_files)} SRT files:\n{srt_files_str}")
        all_sentence_words: list[SentenceWord] = []
        for srt in srt_files:
            print(f"\nProcessing {srt}")
            txt_path: Path = SubtitlesToAnki.__convert_srt_to_txt(srt)
            sentence_words: list[SentenceWord] = SentencesToWords.sentences_to_words_with_part_of_speech(txt_path)
            all_sentence_words.extend(sentence_words)
        print()
        unique_words: list[SentenceWord] = SentencesToWords.find_unique_words(all_sentence_words)
        print(f"Unique words count: {len(unique_words)}")
        return unique_words

    @staticmethod
    def filter_unknown_words(all_words: list[SentenceWord], col_path: str) -> list[SentenceWord]:
        vocabulary_words: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)
        filtered_stop_words: list[SentenceWord] = SentencesToWords.filter_stop_words(all_words)
        unknown_words: list[SentenceWord] = SubtitlesToAnki.__find_unknown_words(filtered_stop_words, vocabulary_words)
        print(f"Unknown words count: {len(unknown_words)}")
        return unknown_words

    @staticmethod
    def format_words(unknown_sentence_words: list[SentenceWord]) -> str:
        lines: list[str] = []
        for unknown_sentence_word in unknown_sentence_words:
            lines.append("")
            lines.append(unknown_sentence_word.lemma)
            lines.append(f"{unknown_sentence_word.pos.name}, {len(unknown_sentence_word.sentences)} sentences:")
            for sentence in unknown_sentence_word.sentences:
                lines.append(sentence)
        return "\n".join(lines)

    @staticmethod
    def __convert_srt_to_txt(srt: Path) -> Path:
        txt: Path = srt.parent / f"{srt.stem}_sentences.txt"
        if not txt.exists():
            SrtToTxt.srt_to_text(srt, txt)
        else:
            print(f"File {txt} already exists")
        return txt

    @staticmethod
    def __find_unknown_words(sentence_words: list[SentenceWord],
                             vocabulary_words: list[VocabularyWord]) -> list[SentenceWord]:
        unknown_words: list[SentenceWord] = []
        for sentence_word in sentence_words:
            lemma: str = sentence_word.lemma
            pos: POS = sentence_word.pos
            exists: bool = False
            for vocabulary_word in vocabulary_words:
                if lemma.lower() in vocabulary_word.word.lower() and pos == vocabulary_word.pos:
                    exists = True
                    break
            if not exists:
                unknown_words.append(sentence_word)
        return unknown_words


if __name__ == "__main__":
    print("Start")

    srt_dir: Path = Path.home() / "tmp" / "Subtitles"
    all_words_file: Path = srt_dir / "words_all.txt"
    all_words: list[SentenceWord] = SubtitlesToAnki.extract_all_words(srt_dir)
    all_words_text: str = SubtitlesToAnki.format_words(all_words)
    all_words_file.write_text(all_words_text)

    col_path: str = "/home/aleks/.local/share/Anki2/User 1/collection.anki2"
    unknown_words_file: Path = srt_dir / "words_unknown.txt"
    unknown_words: list[SentenceWord] = SubtitlesToAnki.filter_unknown_words(all_words, col_path)
    unknown_words_text: str = SubtitlesToAnki.format_words(unknown_words)
    unknown_words_file.write_text(unknown_words_text)

    print("Done")
