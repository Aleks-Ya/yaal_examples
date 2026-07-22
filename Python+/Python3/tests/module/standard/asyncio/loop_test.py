import asyncio
from asyncio import AbstractEventLoop


def test_create_loop():
    event_loop: AbstractEventLoop = asyncio.new_event_loop()
    event_loop.run_until_complete(asyncio.sleep(1))
    event_loop.close()


def test_get_current_loop():
    event_loop: AbstractEventLoop = asyncio.get_event_loop()
    event_loop.run_until_complete(asyncio.sleep(1))
