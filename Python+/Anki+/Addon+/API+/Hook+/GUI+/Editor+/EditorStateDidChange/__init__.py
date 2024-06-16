from aqt import gui_hooks
from aqt.editor import Editor, EditorState
from aqt.utils import showInfo

from ._common.disable import enabled


def _on_change(editor: Editor, new_state: EditorState, old_state: EditorState):
    showInfo(f"Editor state did change: note={editor.note}, old_state={old_state}, new_state={new_state}")


if enabled():
    gui_hooks.editor_state_did_change.append(_on_change)
