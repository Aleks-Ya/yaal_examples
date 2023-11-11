# Iterate all notes
from typing import Sequence

from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def _iterate_all_notes() -> None:
    all_note_ids: Sequence[NoteId] = mw.col.find_notes("")
    total_fields_number: int = 0
    for note_id in all_note_ids:
        note: Note = mw.col.get_note(note_id)
        field_number: int = len(note.fields)
        total_fields_number = total_fields_number + field_number
    showInfo(f"Note number={len(all_note_ids)}, field number={total_fields_number}")


def add_menu_item():
    action = QAction("Iterate all notes", mw)
    qconnect(action.triggered, _iterate_all_notes)
    mw.form.menuTools.addAction(action)
