from pathlib import Path

import pytest

from temp_helper import TempPath


@pytest.fixture
def temp_path_absent() -> Path:
    return TempPath.temp_path_absent()
