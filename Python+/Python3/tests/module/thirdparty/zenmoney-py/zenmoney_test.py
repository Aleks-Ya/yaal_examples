import time

from zenmoney import ZenmoneyRequest
from zenmoney.models import Diff


def test_zenmoney(client: ZenmoneyRequest):
    server_timestamp: int = 0
    current_timestamp: int = int(time.time())
    params: Diff = Diff(
        server_timestamp=server_timestamp,
        current_client_timestamp=current_timestamp,
    )
    data: Diff = client.diff(params=params)
    print(data)
