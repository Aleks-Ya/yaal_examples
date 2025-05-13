from apps.libre_office_draw_search.data_types import FodgPath
from apps.libre_office_draw_search.fodg_parser import FodgParser, FodgFileData


def test_parse_fodg(real_fodg_parser_file: FodgPath):
    data: FodgFileData = FodgParser.parse(real_fodg_parser_file)
    assert data.page_names == ['Page First']
    assert data.texts == ["Cat", "Dog", "русский", "Разный Регистр", "Перенос", "Строки", "SpanText"]
