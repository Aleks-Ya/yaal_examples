from pathlib import Path

import pytest
from openai import OpenAI, AsyncOpenAI


@pytest.fixture
def api_key() -> str:
    return Path.home().joinpath(".openai").joinpath("token.txt").read_text()


@pytest.fixture
def client(api_key: str) -> OpenAI:
    return OpenAI(api_key=api_key)


@pytest.fixture
def async_client(api_key: str) -> AsyncOpenAI:
    return AsyncOpenAI(api_key=api_key)
