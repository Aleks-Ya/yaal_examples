from pathlib import Path

import pytest

from apps.libre_office_draw_search.data_types import FodgPath


@pytest.fixture
def root_dir() -> Path:
    return Path("/tmp/libre")


@pytest.fixture
def root_dir_real() -> FodgPath:
    return FodgPath(Path(__file__).parent / "files")
