# Add tag to a note
from typing import List

from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect, QMenu
from aqt.utils import show_info


def __act() -> None:
    note_id: NoteId = NoteId(1638679140645)
    note: Note = mw.col.get_note(note_id)
    tags_before: List[str] = note.tags
    note.tags.append("leech")
    mw.col.update_note(note)
    show_info(f"Note tag added: nid={note.id}, tags before={tags_before}, tags after={note.tags}")


def add_menu_item(parent_menu: QMenu):
    action: QAction = QAction("Add tag to note", mw)
    qconnect(action.triggered, __act)
    parent_menu.addAction(action)
