from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled


def __on_paste(editor: Editor, html: str, internal: bool, extended: bool):
    items = str([item for item in editor.note.items()]).replace("<", "&lt;")
    html_clean = html.replace("<", "&lt;")
    show_info(f"Editor did paste:\n\n"
             f"editor.note.items={items},\n\n"
             f"html={html_clean},\n\n"
             f" internal={internal}, extended={extended}")


if enabled():
    gui_hooks.editor_did_paste.append(__on_paste)
