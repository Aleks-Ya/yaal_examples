from aqt import gui_hooks, mw
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def _on_init():
    showInfo(f"""
            MainWindow did init.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


def _menu_item_action():
    showInfo('You clicked "MainWindowDidInit item"')


def _add_menu_item():
    menu.add_mw_menu_item("MainWindowDidInit item", _menu_item_action)


if enabled():
    gui_hooks.main_window_did_init.append(_on_init)
    gui_hooks.main_window_did_init.append(_add_menu_item)
