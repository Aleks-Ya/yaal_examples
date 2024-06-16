from anki.notes import Note
from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_event(note: Note):
    showInfo(f"Editor did fire typing timer: note.id={note.id}, note.items={note.items()}")


if enabled():
    gui_hooks.editor_did_fire_typing_timer.append(_on_event)
