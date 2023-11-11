# Show Note details

from anki.notes import NoteId, Note
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def _show_note_details() -> None:
    note_id: NoteId = NoteId(1638679140645)
    note: Note = mw.col.get_note(note_id)
    showInfo(f"Note={note}, id={note.id}, col={note.col}, data={note.data}, fields={note.fields}, "
             f"items={note.items()}, values={note.values()}")


def add_menu_item():
    action = QAction("Show Note details", mw)
    qconnect(action.triggered, _show_note_details)
    mw.form.menuTools.addAction(action)
