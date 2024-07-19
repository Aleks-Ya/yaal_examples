# Show Note details
from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect, QMenu
from aqt.utils import showInfo


def __act() -> None:
    note_id: NoteId = NoteId(1638679140645)
    note: Note = mw.col.get_note(note_id)
    showInfo(f"Note={note}, id={note.id}, col={note.col}, data={note.data}, fields={note.fields}, "
             f"items={note.items()}, values={note.values()}")


def add_menu_item(parent_menu: QMenu):
    action: QAction = QAction("Show Note details", mw)
    qconnect(action.triggered, __act)
    parent_menu.addAction(action)
