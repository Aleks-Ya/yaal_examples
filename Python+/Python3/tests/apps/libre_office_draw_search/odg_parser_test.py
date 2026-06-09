from apps.libre_office_draw_search.data_types import OdgPath
from apps.libre_office_draw_search.odg_parser import OdgParser, OdgFileData


def test_parse_odg(real_odg_parser_file: OdgPath):
    data: OdgFileData = OdgParser.parse(real_odg_parser_file)
    assert data.page_names == ['Page First']
    assert data.texts == ["Cat", "Dog", "русский", "Разный Регистр", "Перенос", "Строки", "SpanText"]
