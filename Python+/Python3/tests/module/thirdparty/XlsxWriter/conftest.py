from pathlib import Path

import pytest
from xlsxwriter import Workbook

from temp_helper import TempPath


@pytest.fixture
def workbook() -> Workbook:
    file: Path = TempPath.temp_path_absent(suffix=".xlsx")
    return Workbook(file)
