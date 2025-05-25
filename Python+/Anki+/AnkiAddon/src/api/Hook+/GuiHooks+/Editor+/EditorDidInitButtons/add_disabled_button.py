from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled


# DOES NOT WORK

def __on_button_click(editor: Editor):
    editor.web.eval("alert('Button Clicked!')")


def __on_init_buttons(buttons: list[str], editor: Editor):
    show_info("Editor did init buttons")
    button: str = editor.addButton(id="disabled_button", label="Disabled button 11", icon=None,
                                   cmd="",
                                   func=__on_button_click,
                                   tip="Disabled Button Tooltip")
    buttons.append(button)


def __on_init(editor: Editor):
    show_info("Editor did init")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')


def __on_load_note(editor: Editor):
    show_info(f"Note was loaded: {editor.note}")
    editor.web.eval('document.getElementById("disabled_button").disabled = true')


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_init_buttons)
    gui_hooks.editor_did_init.append(__on_init)
    gui_hooks.editor_did_load_note.append(__on_load_note)
