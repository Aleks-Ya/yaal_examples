from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo


def on_load_note(editor: Editor, html: str, internal: bool, extended: bool):
    showInfo(f"Editor did paste: note={editor.note}, html={html}, internal={internal}, extended={extended}")


# gui_hooks.editor_did_paste.append(on_load_note)
