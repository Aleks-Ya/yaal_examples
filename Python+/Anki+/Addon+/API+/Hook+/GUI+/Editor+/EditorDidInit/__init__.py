from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo


def on_init(editor: Editor):
    showInfo(f"Editor did init: {editor}")


# gui_hooks.editor_did_init.append(on_init)
