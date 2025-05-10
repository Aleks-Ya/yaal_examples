from pathlib import Path

import pytest
from openai import OpenAI


@pytest.fixture
def client() -> OpenAI:
    key: str = Path.home().joinpath(".openai").joinpath("token.txt").read_text()
    return OpenAI(api_key=key)
