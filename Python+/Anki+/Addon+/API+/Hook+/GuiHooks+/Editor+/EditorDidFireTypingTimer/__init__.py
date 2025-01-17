from anki.notes import Note
from aqt import gui_hooks
from aqt.utils import show_info

from ._common.disable import enabled


def __on_event(note: Note):
    show_info(f"Editor did fire typing timer: note.id={note.id}, note.items={note.items()}")


if enabled():
    gui_hooks.editor_did_fire_typing_timer.append(__on_event)
