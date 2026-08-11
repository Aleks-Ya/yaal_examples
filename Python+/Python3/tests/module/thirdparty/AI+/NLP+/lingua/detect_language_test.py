from lingua import Language, LanguageDetectorBuilder, LanguageDetector

languages: list[Language] = [Language.ENGLISH, Language.FRENCH, Language.JAPANESE, Language.SPANISH, Language.CHINESE]
detector: LanguageDetector = LanguageDetectorBuilder.from_languages(*languages).build()


def test_detect_language():
    language: Language = detector.detect_language_of("languages are awesome")
    assert language == Language.ENGLISH


def test_detect_languages():
    assert detector.detect_language_of('This is a test.') == Language.ENGLISH
    assert detector.detect_language_of('屋台でたこ焼きを食べました。') == Language.JAPANESE
    assert detector.detect_language_of('我喜欢学习语言。') == Language.CHINESE
    assert detector.detect_language_of('El gato duerme en la silla.') == Language.SPANISH
    assert detector.detect_language_of('Bonjour, comment ça va ?') == Language.FRENCH
