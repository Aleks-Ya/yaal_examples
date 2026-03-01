import pytest
from git import Repo

from temp_helper import TempPath


@pytest.fixture
def yaal_examples_repo() -> Repo:
    return Repo(".", search_parent_directories=True)


@pytest.fixture
def repo() -> Repo:
    return Repo.init(TempPath.dir_exists())
