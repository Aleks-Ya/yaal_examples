from aqt import gui_hooks
from aqt.editor import EditorWebView
from aqt.utils import show_info

from ._common.disable import enabled


def __on_init(editor_web_view: EditorWebView):
    show_info(f"EditorWebView did init: {editor_web_view}")


if enabled():
    gui_hooks.editor_web_view_did_init.append(__on_init)
