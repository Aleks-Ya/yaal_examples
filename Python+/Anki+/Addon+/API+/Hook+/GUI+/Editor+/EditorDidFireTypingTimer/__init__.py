from anki.notes import Note
from aqt import gui_hooks
from aqt.utils import showInfo


def on_event(note: Note):
    showInfo(f"Editor did fire typing timer: note.id={note.id}, note.items={note.items()}")


# gui_hooks.editor_did_fire_typing_timer.append(on_event)
