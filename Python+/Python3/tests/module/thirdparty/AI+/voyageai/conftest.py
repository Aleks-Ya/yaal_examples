from pathlib import Path

import pytest
from voyageai import Client


@pytest.fixture
def client() -> Client:
    api_key: str = (Path.home() / ".config" / "voyage-ai-api-key" / "voyage-ai-api-key.txt").read_text()
    return Client(api_key=api_key)
