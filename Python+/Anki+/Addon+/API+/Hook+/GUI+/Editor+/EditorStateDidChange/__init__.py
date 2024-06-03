from aqt import gui_hooks
from aqt.editor import Editor, EditorState
from aqt.utils import showInfo


def on_change(editor: Editor, new_state: EditorState, old_state: EditorState):
    showInfo(f"Editor state did change: note={editor.note}, old_state={old_state}, new_state={new_state}")


# gui_hooks.editor_state_did_change.append(on_change)
