from pathlib import Path

from odfdo import Document

from current_path import get_file_in_current_dir
from temp_helper import TempPath


def test_merge_styles_from(draw_doc: Document):
    src_file: Path = get_file_in_current_dir('SchematizationTemplate.otg')
    src_doc: Document = Document(src_file)
    dest_doc: Document = draw_doc
    dest_doc.merge_styles_from(src_doc)
    file: Path = TempPath.temp_path_absent(".odg")
    dest_doc.save(file)
