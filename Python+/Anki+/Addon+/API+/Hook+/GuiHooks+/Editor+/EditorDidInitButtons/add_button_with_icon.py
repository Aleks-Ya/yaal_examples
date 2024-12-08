from pathlib import Path

from aqt import gui_hooks, mw
from aqt.editor import Editor
from aqt.utils import show_info

from ._common.disable import enabled


def __on_button_click(_: Editor):
    show_info("Icon Button Clicked!")


def __on_init_buttons(buttons: list[str], editor: Editor):
    module_dir: Path = Path(__file__).parent
    module_name: str = module_dir.stem
    addon_dir: Path = Path(mw.addonManager.addonsFolder(module_name))

    icon_path_48px: str = str(addon_dir / "icons" / "numbers_48px.png")
    button_48px: str = editor.addButton(label="",
                                        icon=icon_path_48px,
                                        cmd="icon_48px_button",
                                        func=__on_button_click,
                                        tip="Custom Button With Icon 48 px")
    buttons.append(button_48px)

    icon_path_512px: str = str(addon_dir / "icons" / "numbers_512px.png")
    button_512px: str = editor.addButton(label="",
                                         icon=icon_path_512px,
                                         cmd="icon_512px_button",
                                         func=__on_button_click,
                                         tip="Custom Button With Icon 512 px")
    buttons.append(button_512px)


if enabled():
    gui_hooks.editor_did_init_buttons.append(__on_init_buttons)
