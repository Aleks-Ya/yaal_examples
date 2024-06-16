from aqt import gui_hooks
from aqt.editor import EditorWebView
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_init(editor_web_view: EditorWebView):
    showInfo(f"EditorWebView did init: {editor_web_view}")


if enabled():
    gui_hooks.editor_web_view_did_init.append(_on_init)
