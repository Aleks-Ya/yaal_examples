import timeit

import pytest
from googletrans import Translator


async def __detect_short(translator: Translator) -> None:
    async with translator:
        assert (await translator.detect('This is a test.')).lang == 'en'
        assert (await translator.detect('屋台でたこ焼きを食べました。')).lang == 'ja'
        assert (await translator.detect('我喜欢学习语言。')).lang == 'zh-CN'
        assert (await translator.detect('El gato duerme en la silla.')).lang == 'es'
        assert (await translator.detect('Bonjour, comment ça va ?')).lang == 'fr'


japanese_long_text: str = '屋台でたこ焼きを食べました。' * 10000


async def __detect_long(translator: Translator) -> None:
    assert (await translator.detect(japanese_long_text)).lang == 'ja'


@pytest.mark.asyncio
async def test_detect_languages_short():
    async with Translator() as translator:
        await(__detect_short(translator))  # warmup
        execution_time: float = timeit.timeit(lambda: __detect_short(translator), number=400000)
        print(execution_time)
        assert execution_time <= 1


# NOT WORK: cannot send long text to REST API
@pytest.mark.asyncio
async def test_detect_languages_long():
    async with Translator() as translator:
        await(__detect_long(translator))  # warmup
        execution_time: float = timeit.timeit(lambda: __detect_long(translator), number=5)
        print(execution_time)
        assert execution_time <= 1
