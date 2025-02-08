import pytest
from googletrans import Translator
from googletrans.models import Detected


@pytest.mark.asyncio
async def test_detect_language():
    async with Translator() as translator:
        result: Detected = await translator.detect('hello world!')
        print(result)
        assert result.lang == 'en'
        assert result.confidence > 0.5


@pytest.mark.asyncio
async def test_detect_languages():
    async with Translator() as translator:
        assert (await translator.detect('This is a test.')).lang == 'en'
        assert (await translator.detect('屋台でたこ焼きを食べました。')).lang == 'ja'
        assert (await translator.detect('我喜欢学习语言。')).lang == 'zh-CN'
        assert (await translator.detect('El gato duerme en la silla.')).lang == 'es'
        assert (await translator.detect('Bonjour, comment ça va ?')).lang == 'fr'
