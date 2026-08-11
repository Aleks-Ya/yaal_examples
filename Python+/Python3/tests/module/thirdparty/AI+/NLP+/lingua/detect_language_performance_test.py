import timeit

from lingua import Language, LanguageDetectorBuilder, LanguageDetector

languages: list[Language] = [Language.ENGLISH, Language.FRENCH, Language.JAPANESE, Language.SPANISH, Language.CHINESE]
detector: LanguageDetector = LanguageDetectorBuilder.from_languages(*languages).build()


def __detect_short() -> None:
    assert detector.detect_language_of('This is a test.') == Language.ENGLISH
    assert detector.detect_language_of('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert detector.detect_language_of('我喜欢学习语言。') == Language.CHINESE
    assert detector.detect_language_of('El gato duerme en la silla.') == Language.SPANISH
    assert detector.detect_language_of('Bonjour, comment ça va ?') == Language.FRENCH


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


def __detect_long() -> None:
    assert detector.detect_language_of(japanese_long_text) == Language.JAPANESE


def test_detect_languages_short():
    __detect_short()  # warmup
    execution_time: float = timeit.timeit(__detect_short, number=9000)
    print(execution_time)
    assert execution_time <= 1


def test_detect_languages_long():
    __detect_long()  # warmup
    execution_time: float = timeit.timeit(__detect_long, number=25)
    print(execution_time)
    assert execution_time <= 1
