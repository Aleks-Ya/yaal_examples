from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo

from ._common.disable import enabled


def __on_load_note(editor: Editor):
    showInfo(f"Note was loaded: {editor.note}")


if enabled():
    gui_hooks.editor_did_load_note.append(__on_load_note)
