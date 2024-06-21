from typing import Any

from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import showInfo

from ._common.disable import enabled


def __callback(val: Any):
    showInfo(f"Callback:\n{val}")


def __on_button_click(editor: Editor):
    editor.web.evalWithCallback(
        f"document.getElementById('counter_button').textContent = 'Clicked: successful'", __callback)


def __on_button_click_fail(editor: Editor):
    js: str = """
        try {
            throw new Error("Something went wrong!")
        } catch (error) {
          error.stack
        }
    """
    editor.web.evalWithCallback(js, __callback)


def __on_setup_buttons(buttons: list[str], editor: Editor):
    buttons.append(editor.addButton(id="counter_button", label=f"Callback (success)", icon=None,
                                    cmd="counter_button_cmd_success", func=__on_button_click))
    buttons.append(editor.addButton(id="counter_button_fail", label=f"Callback (fail)", icon=None,
                                    cmd="counter_button_cmd_fail", func=__on_button_click_fail))


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_setup_buttons)
