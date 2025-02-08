import timeit

from langid import classify


def __detect_short() -> None:
    assert classify('This is a test.')[0] == 'en'
    assert classify('屋台でたこ焼きを食べました。')[0] == 'ja'
    assert classify('我喜欢学习语言。')[0] == 'zh'
    assert classify('El gato duerme en la silla.')[0] == 'eo'
    assert classify('Bonjour, comment ça va ?')[0] == 'fr'


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


def __detect_long() -> None:
    assert classify(japanese_long_text)[0] == 'ja'


def test_detect_languages_short():
    __detect_short()  # warmup
    execution_time: float = timeit.timeit(__detect_short, number=200)
    print(execution_time)
    assert execution_time <= 1


def test_detect_languages_long():
    __detect_long()  # warmup
    execution_time: float = timeit.timeit(__detect_long, number=5)
    print(execution_time)
    assert execution_time <= 1
