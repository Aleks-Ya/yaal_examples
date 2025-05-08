from pathlib import Path

from apps.libre_office_draw_search.data_types import FodgPath
from apps.libre_office_draw_search.fodg_parser import FodgParser, FodgFileData


def test_parse_fodg():
    path: FodgPath = FodgPath(Path(__file__).parent / "draw.fodg")
    data: FodgFileData = FodgParser.parse(path)
    assert data.page_names == ['page1']
    assert data.texts == ["Cat"]
