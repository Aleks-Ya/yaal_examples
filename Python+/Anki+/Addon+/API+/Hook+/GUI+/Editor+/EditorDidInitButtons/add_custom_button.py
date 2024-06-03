from aqt import gui_hooks
from aqt.editor import Editor


def on_button_click(editor: Editor):
    editor.web.eval("alert('Button Clicked!')")


def on_setup_buttons(buttons: list[str], editor: Editor):
    button: str = editor.addButton(label="My Button 1", icon=None, cmd="custom_button", func=on_button_click,
                                   tip="Custom Button Tooltip")
    buttons.append(button)


def init():
    gui_hooks.editor_did_init_buttons.append(on_setup_buttons)
