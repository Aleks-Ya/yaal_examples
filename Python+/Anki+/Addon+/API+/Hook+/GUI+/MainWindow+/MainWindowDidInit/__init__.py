from aqt import gui_hooks, mw
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __on_init():
    showInfo(f"""
            MainWindow did init.
            'mw'={mw}
            'mw.col'={mw.col}
            """)


def __menu_item_action():
    showInfo('You clicked "MainWindowDidInit item"')


def __add_menu_item():
    menu.add_mw_menu_item("MainWindowDidInit item", __menu_item_action)


if enabled():
    gui_hooks.main_window_did_init.append(__on_init)
    gui_hooks.main_window_did_init.append(__add_menu_item)
