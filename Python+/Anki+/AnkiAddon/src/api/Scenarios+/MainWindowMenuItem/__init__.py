from aqt.qt import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import show_info

from ._common.disable import enabled


def __show_message():
    show_info('You clicked "Top level menu item"')


def __child_message():
    show_info('You clicked "Child Item"')


def __third_message():
    show_info('You clicked "Third Item"')


def __hotkey_message():
    show_info('You clicked "Menu item with hotkey"')


if enabled():
    # Top-level menu item
    action1: QAction = QAction("Top level menu item", mw)
    qconnect(action1.triggered, __show_message)
    mw.form.menuTools.addAction(action1)

    # Nested menu item
    parent_menu: QMenu = QMenu("Parent Menu", mw)
    mw.form.menuTools.addMenu(parent_menu)
    child_action: QAction = QAction("Child Item", mw)
    qconnect(child_action.triggered, __child_message)
    parent_menu.addAction(child_action)

    # Double-nested menu item
    menu1: QMenu = QMenu("Top Item", mw)
    mw.form.menuTools.addMenu(menu1)
    menu2: QMenu = QMenu("Middle Item", mw)
    menu1.addMenu(menu2)
    action3: QAction = QAction("Third Item", mw)
    qconnect(action3.triggered, __third_message)
    menu2.addAction(action3)

    # Hotkey (&)
    action2: QAction = QAction("Menu item with &hotkey", mw)
    qconnect(action2.triggered, __hotkey_message)
    mw.form.menuTools.addAction(action2)
