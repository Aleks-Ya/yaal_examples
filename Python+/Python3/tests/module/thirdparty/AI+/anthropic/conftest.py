from pathlib import Path
from typing import Callable

import pytest
from anthropic import Anthropic
from anthropic.types import ModelParam, ContentBlock, ThinkingBlock


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


@pytest.fixture
def remove_thinking_blocks() -> Callable[[list[ContentBlock]], list[ContentBlock]]:
    def remove_thinking_block(blocks: list[ContentBlock]) -> list[ContentBlock]:
        return [block for block in blocks if not isinstance(block, ThinkingBlock)]

    return remove_thinking_block


@pytest.fixture
def assert_blocks() -> Callable[..., None]:
    def assert_blocks_no_thinking(blocks: list[ContentBlock], *types: type[ContentBlock]) -> None:
        act_types: list[type[ContentBlock]] = [type(block) for block in blocks]
        assert act_types == list(types)

    return assert_blocks_no_thinking
