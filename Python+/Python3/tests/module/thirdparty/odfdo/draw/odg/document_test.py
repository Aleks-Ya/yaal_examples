from pathlib import Path

from odfdo import Document

from temp_helper import TempPath


def test_create_empty_document():
    doc: Document = Document("odg")
    assert doc is not None


def test_create_save_load_document():
    file: Path = TempPath.temp_path_absent(".odg")
    exp_doc: Document = Document("odg")
    assert exp_doc is not None
    exp_doc.save(file)

    act_doc: Document = Document(file)
    assert act_doc is not None
