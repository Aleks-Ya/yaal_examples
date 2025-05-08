import tempfile
from pathlib import Path

import pytest


@pytest.fixture
def temp_path_absent() -> Path:
    path: Path = Path(tempfile.mkstemp()[1])
    path.unlink()
    assert not path.exists()
    print(f"temp_path_absent={path}")
    return path
