from asyncio import sleep, wait_for, TimeoutError

import pytest


async def main_with_return() -> str:
    return await sleep(1, result='Finished')


@pytest.mark.asyncio
async def test_some_asyncio_code():
    result: str = await main_with_return()
    assert "Finished" == result


@pytest.mark.asyncio
async def test_wait_for_with_timeout():
    try:
        result: str = await wait_for(main_with_return(), timeout=10)
        assert "Finished" == result
    except TimeoutError:
        pytest.fail("The task was not completed within the timeout period.")
