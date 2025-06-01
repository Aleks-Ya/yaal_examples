from pathlib import Path
from typing import Generator

import pytest
from github import Github


@pytest.fixture
def github_authenticated() -> Generator[Github, None, None]:
    token_file: Path = Path.home() / ".github" / "token.txt"
    token: str = token_file.read_text().strip()
    g: Github = Github(token)
    yield g
    g.close()


@pytest.fixture
def github_anonymous() -> Generator[Github, None, None]:
    g: Github = Github()
    yield g
    g.close()
