from pathlib import Path

from app.subtitles_to_anki.anki_vocabulary import VocabularyWord, AnkiVocabulary, POS
from app.subtitles_to_anki.sentences_to_words import SentencesToWords, SentenceWord
from app.subtitles_to_anki.srt_to_txt import SrtToTxt


class SubtitlesToAnki:

    @staticmethod
    def subtitles_to_anki(col_path: str, srt: Path) -> list[SentenceWord]:
        txt: Path = Path.home() / "Downloads" / f"{srt.stem}_sentences.txt"
        if not txt.exists():
            SrtToTxt.srt_to_text(srt, txt)
        else:
            print(f"File {txt} already exists")
        sentence_words: list[SentenceWord] = SentencesToWords.sentences_to_words_with_part_of_speech(txt)
        vocabulary_words: list[VocabularyWord] = AnkiVocabulary.extract_known_vocabulary(col_path)

        unknown_sentence_words: list[SentenceWord] = []
        for sentence_word in sentence_words:
            lemma: str = sentence_word.lemma
            pos: POS = sentence_word.pos
            exists: bool = False
            for vocabulary_word in vocabulary_words:
                if lemma.lower() in vocabulary_word.word.lower() and pos == vocabulary_word.pos:
                    exists = True
                    break
            if not exists:
                unknown_sentence_words.append(sentence_word)

        return unknown_sentence_words

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
