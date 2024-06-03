from anki.notes import Note
from aqt import gui_hooks
from aqt.utils import showInfo


def on_event(changed: bool, note: Note, current_field_idx: int):
    showInfo(f"Editor did unfocus field: note={note}, changed={changed}, current_field_idx={current_field_idx}")


# gui_hooks.editor_did_unfocus_field.append(on_event)
