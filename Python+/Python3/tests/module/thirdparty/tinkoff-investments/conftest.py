from pathlib import Path
from typing import Generator

import pytest
from tinkoff.invest import Client
from tinkoff.invest.services import Services


@pytest.fixture
def client() -> Generator[Services, None, None]:
    token: str = (Path.home() / ".tinkoff" / "token.txt").read_text().strip()
    with Client(token) as client:
        yield client
