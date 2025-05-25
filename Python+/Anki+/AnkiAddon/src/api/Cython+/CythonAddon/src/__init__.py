from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu
from .formatter.strings import upper_case


def __show_addon_info() -> None:
    text: str = upper_case("abc")
    show_info(f"CythonAddon: {text}")


if enabled():
    menu.add_mw_menu_item("Execute Cython function", __show_addon_info)
