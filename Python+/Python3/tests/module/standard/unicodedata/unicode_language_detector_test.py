import timeit

from module.standard.unicodedata.unicode_language_detector import Language
from module.standard.unicodedata.unicode_language_detector import UnicodeLanguageDetector


def test_detect_languages():
    assert UnicodeLanguageDetector.detect_language('This is a test.') == Language.UNKNOWN
    assert UnicodeLanguageDetector.detect_language('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert UnicodeLanguageDetector.detect_language('我喜欢学习语言。') == Language.CHINESE
    assert UnicodeLanguageDetector.detect_language('El gato duerme en la silla.') == Language.UNKNOWN
    assert UnicodeLanguageDetector.detect_language('Bonjour, comment ça va ?') == Language.UNKNOWN
    assert UnicodeLanguageDetector.detect_language('ภาษาไทยเป็นภาษาที่ไพเราะ') == Language.THAI
    assert UnicodeLanguageDetector.detect_language('បេះបិណ្ឌ') == Language.KHMER
    assert UnicodeLanguageDetector.detect_language('안녕하세요') == Language.KOREAN
    assert UnicodeLanguageDetector.detect_language('مرحبا كيف حالك؟') == Language.ARABIC
    assert UnicodeLanguageDetector.detect_language('שלום איך אתה?') == Language.HEBREW


def __detect_short() -> None:
    assert UnicodeLanguageDetector.detect_language('This is a test.') == Language.UNKNOWN
    assert UnicodeLanguageDetector.detect_language('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert UnicodeLanguageDetector.detect_language('我喜欢学习语言。') == Language.CHINESE
    assert UnicodeLanguageDetector.detect_language('El gato duerme en la silla.') == Language.UNKNOWN
    assert UnicodeLanguageDetector.detect_language('Bonjour, comment ça va ?') == Language.UNKNOWN


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


def __detect_long() -> None:
    assert UnicodeLanguageDetector.detect_language(japanese_long_text) == Language.JAPANESE


def test_detect_languages_short():
    __detect_short()  # warmup
    execution_time: float = timeit.timeit(__detect_short, number=16000)
    print(execution_time)
    assert execution_time <= 1


def test_detect_languages_long():
    __detect_long()  # warmup
    execution_time: float = timeit.timeit(__detect_long, number=13)
    print(execution_time)
    assert execution_time <= 1
