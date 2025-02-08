from langid import classify


def test_detect_language():
    language, confidence = classify('This is a test.')
    assert language == 'en'


def test_detect_languages():
    assert classify('This is a test.')[0] == 'en'
    assert classify('屋台でたこ焼きを食べました。')[0] == 'ja'
    assert classify('我喜欢学习语言。')[0] == 'zh'
    assert classify('El gato duerme en la silla.')[0] == 'eo'
    assert classify('Bonjour, comment ça va ?')[0] == 'fr'
