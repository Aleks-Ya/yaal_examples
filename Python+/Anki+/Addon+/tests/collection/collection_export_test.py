import tempfile
from pathlib import Path

import pytest
from anki.collection import Collection, ExportAnkiPackageOptions, ExportLimit
from anki.decks import DeckId
from anki.exporting import TextCardExporter, TextNoteExporter
from anki.models import NoteType
from anki.notes import Note


@pytest.fixture
def origin_col(col: Collection, basic_note_type: NoteType, deck_id: DeckId) -> Collection:
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    return col


def test_export_collection_package(origin_col: Collection):
    package_path: str = str(Path(tempfile.mkdtemp(), "col.colpkg"))
    print(f"Output dir: {package_path}")
    origin_col.export_collection_package(package_path, include_media=True, legacy=False)


def test_export_anki_package(origin_col: Collection):
    package_path: str = str(Path(tempfile.mkdtemp(), "col.apkg"))
    print(f"Output dir: {package_path}")
    options: ExportAnkiPackageOptions = ExportAnkiPackageOptions(with_media=True, legacy=False)
    limit: ExportLimit = None
    origin_col.export_anki_package(out_path=package_path, options=options, limit=limit)


def test_export_text_card(origin_col: Collection):
    exporter: TextCardExporter = TextCardExporter(origin_col)
    output_file: Path = Path(tempfile.mkdtemp(), "file" + TextCardExporter.ext)
    with output_file.open("wb") as f:
        exporter.doExport(f)
    assert output_file.read_text() == "one\ttwo\n"


def test_export_text_note(origin_col: Collection):
    exporter: TextNoteExporter = TextNoteExporter(origin_col)
    output_file: Path = Path(tempfile.mkdtemp(), "file" + TextCardExporter.ext)
    with output_file.open("wb") as f:
        exporter.doExport(f)
    assert output_file.read_text() == "one\ttwo\t"
