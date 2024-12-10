from asyncio import sleep, wait_for, TimeoutError

import pytest


async def main_with_return() -> str:
    await sleep(1)
    return 'Finished'


@pytest.mark.asyncio
async def test_some_asyncio_code():
    res: str = await main_with_return()
    assert "Finished" == res


@pytest.mark.asyncio
async def test_wait_for_with_timeout():
    try:
        res: str = await wait_for(main_with_return(), timeout=10)
        assert "Finished" == res
    except TimeoutError:
        pytest.fail("The task was not completed within the timeout period.")
