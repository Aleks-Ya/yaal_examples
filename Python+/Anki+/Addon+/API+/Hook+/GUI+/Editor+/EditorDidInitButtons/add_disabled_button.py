from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo


# DOES NOT WORK

def _on_button_click(editor: Editor):
    editor.web.eval("alert('Button Clicked!')")


def _on_setup_buttons(buttons: list[str], editor: Editor):
    showInfo("Editor did init buttons")
    button: str = editor.addButton(id="disabled_button", label="Disabled button 11", icon=None,
                                   cmd="",
                                   func=_on_button_click,
                                   tip="Disabled Button Tooltip")
    buttons.append(button)


def init3():
    gui_hooks.editor_did_init_buttons.append(_on_setup_buttons)
    gui_hooks.editor_did_init.append(_on_init)
    gui_hooks.editor_did_load_note.append(_on_load_note)


def _on_init(editor: Editor):
    showInfo("Editor did init")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')


def _on_load_note(editor: Editor):
    showInfo(f"Note was loaded: {editor.note}")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')
