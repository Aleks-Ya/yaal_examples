import asyncio


def test_sleep():
    async def main():
        print('Hello ...')
        await asyncio.sleep(1)
        print('... World!')

    asyncio.run(main())


def test_return_value():
    async def main_with_return() -> str:
        await asyncio.sleep(1)
        return 'Finished'

    result: str = asyncio.run(main_with_return())
    assert result == 'Finished'
