from pathlib import Path

import pytest
from google.genai import Client


@pytest.fixture
def client() -> Client:
    api_key: str = (Path.home() / ".gcp" / "api_key.txt").read_text().strip()
    return  Client(api_key=api_key)
