import asyncio

import requests
from requests import Response

events: list[str] = []


async def get_data_long():
    events.append('Before request LONG')
    response: Response = await asyncio.to_thread(
        requests.get, 'https://httpbin.org/delay/5'
    )
    events.append(f'After request LONG: {response.status_code}')


async def get_data_short():
    events.append('Before request SHORT')
    response: Response = await asyncio.to_thread(
        requests.get, 'https://httpbin.org/delay/2'
    )
    events.append(f'After request SHORT: {response.status_code}')


async def main():
    await asyncio.gather(
        get_data_long(),
        get_data_short(),
    )


events.append('Before main')
asyncio.run(main())
events.append('After main')

expected_events: list[str] = [
    'Before main',
    'Before request LONG',
    'Before request SHORT',
    'After request SHORT: 200',
    'After request LONG: 200',
    'After main',
]
assert events == expected_events, f'{events} != {expected_events}'
