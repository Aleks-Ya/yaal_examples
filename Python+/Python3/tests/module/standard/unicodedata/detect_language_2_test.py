import unicodedata


def __detect_language(text: str) -> str:
    has_chinese: bool = any('CJK UNIFIED' in unicodedata.name(char) for char in text)
    has_japanese: bool = any(
        'HIRAGANA' in unicodedata.name(char) or 'KATAKANA' in unicodedata.name(char) for char in text)

    if has_chinese:
        return "Chinese"
    elif has_japanese:
        return "Japanese"
    else:
        return "Unknown"


def test_detect_languages():
    assert __detect_language('This is a test.') == "Unknown"
    assert __detect_language('屋台でたこ焼きを食べました。') == "Chinese"
    assert __detect_language('我喜欢学习语言。') == "Chinese"
    assert __detect_language('El gato duerme en la silla.') == "Unknown"
    assert __detect_language('Bonjour, comment ça va ?') == "Unknown"
