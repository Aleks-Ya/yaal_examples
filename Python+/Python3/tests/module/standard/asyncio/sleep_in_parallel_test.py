import asyncio
from _asyncio import Future
from types import CoroutineType
from typing import Any


def test_sleep_in_parallel():
    events: list[str] = []

    async def sleep5():
        events.append('Started: 5')
        await asyncio.sleep(5)
        events.append('Finished: 5')

    async def sleep3():
        events.append('Started: 3')
        await asyncio.sleep(3)
        events.append('Finished: 3')

    async def main():
        sleep_5: CoroutineType[Any, Any, None] = sleep5()
        sleep_3: CoroutineType[Any, Any, None] = sleep3()
        gather: Future[tuple[None, None]] = asyncio.gather(sleep_5, sleep_3)
        await gather

    asyncio.run(main())
    assert events == [
        'Started: 5',
        'Started: 3',
        'Finished: 3',
        'Finished: 5',
    ]
