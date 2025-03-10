# Iterate all notes
from typing import Sequence

from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect, QMenu
from aqt.utils import show_info


def __act() -> None:
    all_note_ids: Sequence[NoteId] = mw.col.find_notes("")
    total_fields_number: int = 0
    for note_id in all_note_ids:
        note: Note = mw.col.get_note(note_id)
        field_number: int = len(note.fields)
        total_fields_number = total_fields_number + field_number
    show_info(f"Note number={len(all_note_ids)}, field number={total_fields_number}")


def add_menu_item(parent_menu: QMenu):
    action: QAction = QAction("Iterate all notes", mw)
    qconnect(action.triggered, __act)
    parent_menu.addAction(action)
