from pathlib import Path
from typing import Generator

import pytest
from t_tech.invest import Client
from t_tech.invest.services import Services


@pytest.fixture
def client() -> Generator[Services, None, None]:
    token: str = (Path.home() / ".tinkoff" / "token.txt").read_text().strip()
    with Client(token) as client:
        yield client
