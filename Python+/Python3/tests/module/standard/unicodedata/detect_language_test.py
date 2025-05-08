import unicodedata


def __categories(text: str) -> dict[str, int]:
    categories: dict[str, int] = {}
    for char in text:
        category: str = unicodedata.category(char)
        categories[category] = categories.get(category, 0) + 1
    return categories


def test_detect_language():
    result: dict[str, int] = __categories("This is a test.")
    assert result == {'Ll': 10, 'Lu': 1, 'Po': 1, 'Zs': 3}


def test_detect_languages():
    assert __categories('This is a test.') == {'Lu': 1, 'Ll': 10, 'Zs': 3, 'Po': 1}
    assert __categories('屋台でたこ焼きを食べました。') == {'Lo': 13, 'Po': 1}
    assert __categories('我喜欢学习语言。') == {'Lo': 7, 'Po': 1}
    assert __categories('El gato duerme en la silla.') == {'Ll': 20, 'Lu': 1, 'Po': 1, 'Zs': 5}
    assert __categories('Bonjour, comment ça va ?') == {'Ll': 17, 'Lu': 1, 'Po': 2, 'Zs': 4}
