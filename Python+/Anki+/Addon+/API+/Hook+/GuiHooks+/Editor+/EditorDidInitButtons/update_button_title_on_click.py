from aqt import gui_hooks
from aqt.editor import Editor

from ._common.disable import enabled

__counter: int = 0


def __on_button_click(editor: Editor):
    global __counter
    __counter += 1
    editor.web.eval(f"document.getElementById('counter_button').textContent = 'Clicked: {__counter}'")


def __on_init_buttons(buttons: list[str], editor: Editor):
    button: str = editor.addButton(id="counter_button", label=f"Click to increment", icon=None,
                                   cmd="counter_button_cmd",
                                   func=__on_button_click,
                                   tip="Click to see details")
    buttons.append(button)


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_init_buttons)
