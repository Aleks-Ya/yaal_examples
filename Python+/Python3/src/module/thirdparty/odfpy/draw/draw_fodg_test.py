from pathlib import Path

from src.module.thirdparty.odfpy.draw.fodg_parser import FodgParser, Data


def test_find_all_texts():
    data: Data = FodgParser.parse(Path('Draw_Flat_XML_ODF.fodg'))
    assert data == Data(page_names=['Page One', 'Page Two'],
                        texts=['Hello World', 'Bye World', 'take care', 'see you'])
