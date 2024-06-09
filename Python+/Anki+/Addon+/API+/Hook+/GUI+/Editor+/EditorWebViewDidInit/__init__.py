from aqt import gui_hooks
from aqt.editor import EditorWebView
from aqt.utils import showInfo


def on_init(editor_web_view: EditorWebView):
    showInfo(f"EditorWebView did init: {editor_web_view}")


# gui_hooks.editor_web_view_did_init.append(on_init)
