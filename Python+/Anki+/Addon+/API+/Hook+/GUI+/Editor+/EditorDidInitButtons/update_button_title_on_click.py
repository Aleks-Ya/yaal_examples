from aqt import gui_hooks
from aqt.editor import Editor

counter: int = 0


def on_button_click(editor: Editor):
    global counter
    counter += 1
    editor.web.eval(f"document.getElementById('counter_button').textContent = 'Clicked: {counter}'")


def on_setup_buttons(buttons: list[str], editor: Editor):
    button: str = editor.addButton(id="counter_button", label=f"Click to increment", icon=None,
                                   cmd="counter_button_cmd",
                                   func=on_button_click,
                                   tip="Click to see details")
    buttons.append(button)


def init2():
    gui_hooks.editor_did_init_buttons.append(on_setup_buttons)
