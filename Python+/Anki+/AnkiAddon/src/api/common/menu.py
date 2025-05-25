import os
from functools import partial
from pathlib import Path
from typing import Callable

from aqt import mw, gui_hooks
from aqt.browser import Browser
from aqt.qt import QAction, QMenu, QMenuBar, qconnect


def add_mw_menu_item(item_name: str, item_action: Callable) -> None:
    top_menu: QMenu = __create_top_menu(mw.form.menubar)
    addon_top_menu: QMenu = __create_addon_menu(top_menu, mw.form.menubar)

    action: QAction = QAction(item_name, mw)
    qconnect(action.triggered, item_action)
    addon_top_menu.addAction(action)


def add_browser_menu(item_name: str, item_action: Callable):
    partial_func = partial(__add_item_to_edit_menu, item_name, item_action)
    gui_hooks.browser_will_show.append(partial_func)


def __create_top_menu(menubar: QMenuBar) -> QMenu:
    top_name: str = "Addon API"
    top_menu: QMenu = menubar.findChild(QMenu, top_name) or QMenu(top_name, menubar)
    top_menu.setObjectName(top_name)
    menubar.addMenu(top_menu)
    return top_menu


def __create_addon_menu(top_menu: QMenu, menubar: QMenuBar) -> QMenu:
    addon_name: str = os.path.basename(Path(__file__).parent.parent)
    addon_top_menu: QMenu = menubar.findChild(QMenu, addon_name) or QMenu(addon_name, menubar)
    addon_top_menu.setObjectName(addon_name)
    top_menu.addMenu(addon_top_menu)
    return addon_top_menu


def __add_item_to_edit_menu(item_name: str, item_action: Callable, browser: Browser) -> None:
    top_menu: QMenu = __create_top_menu(browser.form.menubar)
    addon_top_menu: QMenu = __create_addon_menu(top_menu, browser.form.menubar)

    action: QAction = QAction(item_name, browser)
    qconnect(action.triggered, lambda: item_action(browser))
    addon_top_menu.addAction(action)
