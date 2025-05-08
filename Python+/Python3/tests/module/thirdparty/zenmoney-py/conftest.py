from pathlib import Path

import pytest
from zenmoney import ZenmoneyRequest


@pytest.fixture
def client() -> ZenmoneyRequest:
    token: str = (Path.home() / ".zenmoney" / "token.txt").read_text().strip()
    return ZenmoneyRequest(token)
