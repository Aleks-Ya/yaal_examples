from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo


def on_load_note(editor: Editor):
    showInfo(f"Note was loaded: {editor.note}")


# gui_hooks.editor_did_load_note.append(on_load_note)
