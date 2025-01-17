from typing import Sequence

from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect, QMenu
from aqt.utils import show_info


def __act() -> None:
    note_ids: Sequence[NoteId] = mw.col.find_notes("deck:*")
    total_size: int = 0
    for note_id in note_ids:
        note: Note = mw.col.get_note(note_id)
        size: int = sum([len(field.encode()) for field in note.fields])
        total_size += size
    show_info(f"Collection size: {total_size:,}")


def add_menu_item(parent_menu: QMenu):
    action: QAction = QAction("Show Collection size", mw)
    qconnect(action.triggered, __act)
    parent_menu.addAction(action)
