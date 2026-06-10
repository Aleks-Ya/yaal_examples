from pathlib import Path

from odfdo import Document, DrawPage, Paragraph

from current_path import get_file_in_current_dir

file: Path = get_file_in_current_dir('draw.odg')
doc: Document = Document(file)


def test_find_all_texts():
    text_elements: list[Paragraph] = doc.body.get_paragraphs()
    texts: list[str] = [element.text for element in text_elements if element.text is not None]

    page_elements: list[DrawPage] = doc.body.get_draw_pages()
    page_names: list[str | None] = [page.name for page in page_elements]

    assert page_names == ['Page One', 'Page Two', 'My styles']
    assert texts == ['Hello World', 'Bye World', 'take care', 'see you', 'Styled Text', 'My hidden style']
