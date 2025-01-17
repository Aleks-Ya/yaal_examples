from aqt import gui_hooks
from aqt.editor import Editor, EditorState
from aqt.utils import show_info

from ._common.disable import enabled


def __on_change(editor: Editor, new_state: EditorState, old_state: EditorState):
    show_info(f"Editor state did change: note={editor.note}, old_state={old_state}, new_state={new_state}")


if enabled():
    gui_hooks.editor_state_did_change.append(__on_change)
