from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_init(editor: Editor):
    showInfo(f"Editor did init: {editor}")


if enabled():
    gui_hooks.editor_did_init.append(_on_init)
