from anki.notes import Note
from aqt import gui_hooks
from aqt.utils import showInfo

from ._common.disable import enabled


def __on_unfocus_field(changed: bool, note: Note, current_field_idx: int):
    showInfo(f"Editor did unfocus field: note={note}, changed={changed}, current_field_idx={current_field_idx}")


if enabled():
    gui_hooks.editor_did_unfocus_field.append(__on_unfocus_field)
