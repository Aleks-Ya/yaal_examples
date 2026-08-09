from pathlib import Path

import pytest
from anthropic import Anthropic
from anthropic.types import ModelParam


@pytest.fixture
def client() -> Anthropic:
    key_file: Path = Path.home() / ".config" / "anthropic-api-key" / "personal-anthropic-api-key.txt"
    key: str = key_file.read_text().strip()
    client: Anthropic = Anthropic(api_key=key)
    client.models.list(limit=1)  # verify authentication
    return client


@pytest.fixture
def model() -> ModelParam:
    return "claude-sonnet-5"


@pytest.fixture
def max_tokens() -> int:
    return 2014
