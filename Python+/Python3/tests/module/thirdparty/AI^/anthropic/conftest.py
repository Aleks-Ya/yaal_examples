import pytest
from anthropic import Anthropic


@pytest.fixture
def client() -> Anthropic:
    return Anthropic()

@pytest.fixture
def model() -> str:
    return "claude-sonnet-5"
