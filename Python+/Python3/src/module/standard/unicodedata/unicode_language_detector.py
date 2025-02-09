from enum import Enum


class Language(Enum):
    CHINESE = "Chinese"
    JAPANESE = "Japanese"
    THAI = "Thai"
    KHMER = "Khmer"
    KOREAN = "Korean"
    ARABIC = "Arabic"
    HEBREW = "Hebrew"
    UNKNOWN = "Unknown"


class UnicodeLanguageDetector:

    @staticmethod
    def detect_language(text: str) -> Language:
        counts: dict[Language, int] = {
            Language.CHINESE: 0,
            Language.JAPANESE: 0,
            Language.THAI: 0,
            Language.KHMER: 0,
            Language.KOREAN: 0,
            Language.ARABIC: 0,
            Language.HEBREW: 0
        }

        char_language_cache: dict[str, Language] = {}
        for char in text:
            if char in char_language_cache:
                language: Language = char_language_cache[char]
            else:
                language: Language = UnicodeLanguageDetector.__char_to_language(char)
                char_language_cache[char] = language
            if language and language != Language.UNKNOWN:
                counts[language] += 1

        detected_language: Language = max(counts, key=counts.get)
        return detected_language if counts[detected_language] > 0 else Language.UNKNOWN

    @staticmethod
    def __char_to_language(char: str) -> Language:
        code_point: int = ord(char)
        if 0x4E00 <= code_point <= 0x9FFF:  # CJK Unified Ideographs
            return Language.CHINESE
        elif 0x3040 <= code_point <= 0x309F or 0x30A0 <= code_point <= 0x30FF:  # Hiragana and Katakana
            return Language.JAPANESE
        elif 0x0E00 <= code_point <= 0x0E7F:  # Thai
            return Language.THAI
        elif 0x1780 <= code_point <= 0x17FF:  # Khmer
            return Language.KHMER
        elif 0xAC00 <= code_point <= 0xD7AF:  # Hangul Syllables
            return Language.KOREAN
        elif 0x0600 <= code_point <= 0x06FF:  # Arabic
            return Language.ARABIC
        elif 0x0590 <= code_point <= 0x05FF:  # Hebrew
            return Language.HEBREW
        else:
            return Language.UNKNOWN
