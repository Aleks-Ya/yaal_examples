from polyglot.text import Text


def test_detect_language():
    text: Text = Text('This is a test.')
    assert text.language.code == 'en'


def test_detect_languages():
    assert Text('This is a test.').language.code == 'en'
    assert Text('屋台でたこ焼きを食べました。').language.code == 'ja'
    assert Text('我喜欢学习语言。').language.code == 'zh-cn'
    assert Text('El gato duerme en la silla.').language.code == 'es'
    assert Text('Bonjour, comment ça va ?').language.code == 'fr'
