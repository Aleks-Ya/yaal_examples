import timeit

from module.standard.unicodedata.detect_language_3 import detect_language_3, Language


def test_detect_languages():
    assert detect_language_3('This is a test.') == Language.UNKNOWN
    assert detect_language_3('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert detect_language_3('我喜欢学习语言。') == Language.CHINESE
    assert detect_language_3('El gato duerme en la silla.') == Language.UNKNOWN
    assert detect_language_3('Bonjour, comment ça va ?') == Language.UNKNOWN
    assert detect_language_3('ภาษาไทยเป็นภาษาที่ไพเราะ') == Language.THAI
    assert detect_language_3('បេះបិណ្ឌ') == Language.KHMER
    assert detect_language_3('안녕하세요') == Language.KOREAN
    assert detect_language_3('مرحبا كيف حالك؟') == Language.ARABIC
    assert detect_language_3('שלום איך אתה?') == Language.HEBREW


def __detect_short() -> None:
    assert detect_language_3('This is a test.') == Language.UNKNOWN
    assert detect_language_3('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert detect_language_3('我喜欢学习语言。') == Language.CHINESE
    assert detect_language_3('El gato duerme en la silla.') == Language.UNKNOWN
    assert detect_language_3('Bonjour, comment ça va ?') == Language.UNKNOWN


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


def __detect_long() -> None:
    assert detect_language_3(japanese_long_text) == Language.JAPANESE


def test_detect_languages_short():
    __detect_short()  # warmup
    execution_time: float = timeit.timeit(__detect_short, number=17000)
    print(execution_time)
    assert execution_time <= 1


def test_detect_languages_long():
    __detect_long()  # warmup
    execution_time: float = timeit.timeit(__detect_long, number=15)
    print(execution_time)
    assert execution_time <= 1
