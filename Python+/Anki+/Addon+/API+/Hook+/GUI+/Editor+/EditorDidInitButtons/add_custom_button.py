from aqt import gui_hooks
from aqt.editor import Editor

from ._common.disable import enabled


def __on_button_click(editor: Editor):
    editor.web.eval("alert('Button Clicked!')")


def __on_setup_buttons(buttons: list[str], editor: Editor):
    button: str = editor.addButton(label="My Button 1", icon=None, cmd="custom_button", func=__on_button_click,
                                   tip="Custom Button Tooltip")
    buttons.append(button)


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_setup_buttons)
