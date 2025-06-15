from pathlib import Path

from app.subtitles_to_anki.srt.sentences_to_words import SentencesToWords, SentenceWord
from app.subtitles_to_anki.srt.srt_to_txt import SrtToTxt
from app.subtitles_to_anki.word.unknown_words import UnknownWords
from app.subtitles_to_anki.word.word_formatter import WordFormatter


class AllWords:
    def __init__(self, out_dir: Path, unknown_words: UnknownWords):
        self.__out_dir: Path = out_dir
        self.__unknown_words: UnknownWords = unknown_words

    def extract_words(self, srt_dir: Path) -> list[SentenceWord]:
        self.__out_dir.mkdir(parents=True, exist_ok=True)
        all_words_file: Path = self.__out_dir / "words_all.txt"
        all_words: list[SentenceWord] = self.__extract_all_words(srt_dir)
        all_words_text: str = WordFormatter.format_words(all_words)
        all_words_file.write_text(all_words_text)
        return all_words

    def __extract_all_words(self, srt_dir: Path) -> list[SentenceWord]:
        srt_files: list[Path] = list(srt_dir.glob("*.srt"))
        srt_files_str: str = "\n".join([f"{srt}" for srt in srt_files])
        print(f"\nFound {len(srt_files)} SRT files:\n{srt_files_str}")
        all_sentence_words: list[SentenceWord] = []
        for srt_file in srt_files:
            print(f"\nProcessing {srt_file}")
            sentence_words: list[SentenceWord] = self.__extract_words(srt_file)
            all_sentence_words.extend(sentence_words)
        print()
        unique_words: list[SentenceWord] = SentencesToWords.find_unique_words(all_sentence_words)
        print(f"Unique words count: {len(unique_words)}")
        return unique_words

    def __extract_words(self, srt_file: Path) -> list[SentenceWord]:
        print(f"\nProcessing {srt_file}")
        srt_subdir: Path = self.__out_dir / srt_file.stem
        srt_subdir.mkdir(parents=True, exist_ok=True)
        txt_path: Path = self.__convert_srt_to_txt(srt_file, srt_subdir)
        sentence_words: list[SentenceWord] = SentencesToWords.sentences_to_words_with_part_of_speech(txt_path)
        sentence_words_file: Path = srt_subdir / "words_all.txt"
        unique_words: list[SentenceWord] = SentencesToWords.find_unique_words(sentence_words)
        all_words_text: str = WordFormatter.format_words(unique_words)
        print(f"Writing words to {sentence_words_file}")
        sentence_words_file.write_text(all_words_text)
        unknown_words_file: Path = srt_subdir / "words_unknown.txt"
        unknown_words: list[SentenceWord] = self.__unknown_words.filter_unknown_words(unique_words)
        unknown_words_text: str = WordFormatter.format_words(unknown_words)
        print(f"Writing words to {unknown_words_file}")
        unknown_words_file.write_text(unknown_words_text)
        return sentence_words

    @staticmethod
    def __convert_srt_to_txt(srt: Path, srt_subdir: Path) -> Path:
        txt: Path = srt_subdir / "sentences.txt"
        if not txt.exists():
            SrtToTxt.srt_to_text(srt, txt)
        else:
            print(f"File {txt} already exists")
        return txt
