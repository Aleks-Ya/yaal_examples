from pathlib import Path

from ooodev.write import WriteDoc, WriteTextCursor

from temp_helper import TempPath


def test_create_text_document():
    doc: WriteDoc = WriteDoc.from_current_doc()
    cursor: WriteTextCursor[WriteDoc] = doc.get_cursor()
    cursor.append_para("Hello World")
    out_file: Path = TempPath.temp_path_absent(".odt")
    print(f"out_file={out_file}")
    doc.save_doc(out_file)
    doc.close_doc()
