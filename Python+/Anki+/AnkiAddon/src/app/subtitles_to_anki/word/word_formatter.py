from app.subtitles_to_anki.srt.sentences_to_words import SentenceWord


class WordFormatter:

    @staticmethod
    def format_words(unknown_sentence_words: list[SentenceWord]) -> str:
        lines: list[str] = []
        for unknown_sentence_word in unknown_sentence_words:
            lines.append("")
            lines.append(f'''"{unknown_sentence_word.lemma}"''')
            lines.append(f"{unknown_sentence_word.pos.name}, {len(unknown_sentence_word.sentences)} sentences:")
            for sentence in unknown_sentence_word.sentences:
                lines.append(sentence)
        return "\n".join(lines)
