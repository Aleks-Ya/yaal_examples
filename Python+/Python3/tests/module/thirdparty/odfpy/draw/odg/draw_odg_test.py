from pathlib import Path
from typing import Any

from odf.opendocument import load, OpenDocument
from odf import draw, text

from current_path import get_file_in_current_dir


def test_find_all_texts():
    file: Path = get_file_in_current_dir('draw.odg')
    doc: OpenDocument = load(file)

    text_elements: list[Any] | Any = doc.getElementsByType(text.P)
    texts: list[Any] = [element.firstChild.data for element in text_elements if element.firstChild is not None]

    page_elements: list[Any] | Any = doc.getElementsByType(draw.Page)
    page_names: list[Any] = [page.getAttribute('name') for page in page_elements]

    assert page_names == ['Page_20_One', 'Page_20_Two']  # space is encoded as "_20_"
    assert texts == ['Hello World', 'Bye World', 'take care', 'see you']
