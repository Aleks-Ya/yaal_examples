from PyQt6.QtGui import QAction
from PyQt6.QtWidgets import QMenu
from aqt import gui_hooks, qconnect
from aqt.browser import Browser
from aqt.utils import showInfo

from ._common.disable import enabled


def _show_message_1():
    showInfo('You clicked one-level Edit menu item')


def _show_message_2():
    showInfo('You clicked "Child item 1"')


def _show_message_3():
    showInfo('You clicked "Custom Child item 1"')


def _add_item_to_edit_menu(browser: Browser) -> None:
    action: QAction = QAction("My menu item 1", browser)
    qconnect(action.triggered, _show_message_1)
    browser.form.menuEdit.addAction(action)


def _add_multi_level_item_to_edit_menu(browser: Browser) -> None:
    parent_menu: QMenu = QMenu("Parent Menu 1", browser)
    browser.form.menuEdit.addMenu(parent_menu)
    child_action: QAction = QAction("Child Item 1", browser)
    qconnect(child_action.triggered, _show_message_2)
    parent_menu.addAction(child_action)


def _add_item_to_custom_menu(browser: Browser) -> None:
    parent_menu: QMenu = QMenu("Custom Menu 1", browser)
    browser.form.menubar.addMenu(parent_menu)
    child_action: QAction = QAction("Custom Child Item 1", browser)
    qconnect(child_action.triggered, _show_message_3)
    parent_menu.addAction(child_action)


if enabled():
    gui_hooks.browser_will_show.append(_add_item_to_edit_menu)
    gui_hooks.browser_will_show.append(_add_multi_level_item_to_edit_menu)
    gui_hooks.browser_will_show.append(_add_item_to_custom_menu)
