import timeit

from langdetect import detect


def __detect_short() -> None:
    assert detect('This is a test.') == 'en'
    assert detect('屋台でたこ焼きを食べました。') == 'ja'
    assert detect('我喜欢学习语言。') == 'zh-cn'
    assert detect('El gato duerme en la silla.') == 'es'
    assert detect('Bonjour, comment ça va ?') == 'fr'


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


def __detect_long() -> None:
    assert detect(japanese_long_text) == 'ja'


def test_detect_languages_short():
    __detect_short()  # warmup
    execution_time: float = timeit.timeit(__detect_short, number=70)
    print(execution_time)
    assert execution_time <= 1


def test_detect_languages_long():
    __detect_long()  # warmup
    execution_time: float = timeit.timeit(__detect_long, number=20)
    print(execution_time)
    assert execution_time <= 1
