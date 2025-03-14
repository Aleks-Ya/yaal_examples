from langdetect import detect


def test_detect_language():
    language: str = detect('This is a test.')
    assert language == 'en'


def test_detect_languages():
    assert detect('This is a test.') == 'en'
    assert detect('屋台でたこ焼きを食べました。') == 'ja'
    assert detect('我喜欢学习语言。') == 'zh-cn'
    assert detect('El gato duerme en la silla.') == 'es'
    assert detect('Bonjour, comment ça va ?') == 'fr'
