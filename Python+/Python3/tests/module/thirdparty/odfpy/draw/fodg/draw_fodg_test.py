from current_path import get_file_in_current_dir
from module.thirdparty.odfpy.draw.fodg.fodg_parser import Data, FodgParser


def test_find_all_texts():
    data: Data = FodgParser.parse(get_file_in_current_dir('draw.fodg'))
    assert data == Data(page_names=['Page One', 'Page Two'],
                        texts=['Hello World', 'Bye World', 'take care', 'see you'])
