from pathlib import Path

import pytest
from odfdo import Document

from current_path import get_file_in_current_dir


@pytest.fixture
def draw_doc() -> Document:
    file: Path = get_file_in_current_dir('draw.odg')
    return Document(file)
