from aqt.qt import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from ._common.disable import enabled


def __show_message():
    showInfo('You clicked "Top level menu item"')


def __child_message():
    showInfo('You clicked "Child Item"')


def __hotkey_message():
    showInfo('You clicked "Menu item with hotkey"')


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

    # Hotkey (&)
    action2: QAction = QAction("Menu item with &hotkey", mw)
    qconnect(action2.triggered, __hotkey_message)
    mw.form.menuTools.addAction(action2)
