from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo


# DOES NOT WORK

def on_button_click(editor: Editor):
    editor.web.eval("alert('Button Clicked!')")


def on_setup_buttons(buttons: list[str], editor: Editor):
    showInfo("Editor did init buttons")
    button: str = editor.addButton(id="disabled_button", label="Disabled button 11", icon=None,
                                   cmd="",
                                   func=on_button_click,
                                   tip="Disabled Button Tooltip")
    buttons.append(button)


def init3():
    gui_hooks.editor_did_init_buttons.append(on_setup_buttons)
    gui_hooks.editor_did_init.append(on_init)
    gui_hooks.editor_did_load_note.append(on_load_note)


def on_init(editor: Editor):
    showInfo("Editor did init")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')


def on_load_note(editor: Editor):
    showInfo(f"Note was loaded: {editor.note}")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')
