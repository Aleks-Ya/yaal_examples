from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled


def __on_init(editor: Editor):
    show_info(f"Editor did init: {editor}")


if enabled():
    gui_hooks.editor_did_init.append(__on_init)
