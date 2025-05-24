from tempfile import mkstemp
from pathlib import Path
from typing import Optional

import pytest


class TempPath:
    @staticmethod
    def temp_path_absent(suffix: Optional[str] = ".tmp") -> Path:
        return TempPath.__temp_path_absent(suffix=suffix)

    @staticmethod
    def __temp_path_absent(suffix: Optional[str] = None) -> Path:
        path: Path = Path(mkstemp(suffix=suffix)[1])
        path.unlink()
        assert not path.exists()
        print(f"temp_path_absent={path}")
        return path


@pytest.fixture
def temp_path_absent() -> Path:
    return TempPath.temp_path_absent()
