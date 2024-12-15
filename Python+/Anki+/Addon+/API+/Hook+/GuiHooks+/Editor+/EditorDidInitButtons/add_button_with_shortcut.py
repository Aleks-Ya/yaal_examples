from aqt import gui_hooks
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled


def __on_button_click_1(_: Editor):
    show_info("Shortcut Button Clicked #1")


def __on_button_click_2(_: Editor):
    show_info("Shortcut Button Clicked #2")


def __on_init_buttons(buttons: list[str], editor: Editor):
    keys1: str = "Ctrl+Alt+Shift+M"
    button1: str = editor.addButton(label=f"Shortcut {keys1}",
                                    keys=keys1,
                                    cmd="shortcut1",
                                    func=__on_button_click_1,
                                    icon=None)
    buttons.append(button1)

    keys2: str = "Ctrl+T"
    button2: str = editor.addButton(label=f"Shortcut {keys2}",
                                    keys=keys2,
                                    cmd="shortcut2",
                                    func=__on_button_click_2,
                                    icon=None)
    buttons.append(button2)


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_init_buttons)
