from PyQt6.QtWidgets import QMenu
from aqt import mw
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def show_message():
    showInfo('You clicked "Top level menu item"')


def child_message():
    showInfo('You clicked "Child Item"')


def hotkey_message():
    showInfo('You clicked "Menu item with hotkey"')


# Top-level menu item
action1 = QAction("Top level menu item", mw)
qconnect(action1.triggered, show_message)
mw.form.menuTools.addAction(action1)

# Nested menu item
parent_menu = QMenu("Parent Menu", mw)
mw.form.menuTools.addMenu(parent_menu)
child_action = QAction("Child Item", mw)
qconnect(child_action.triggered, child_message)
parent_menu.addAction(child_action)

# Hotkey (&)
action2 = QAction("Menu item with &hotkey", mw)
qconnect(action2.triggered, hotkey_message)
mw.form.menuTools.addAction(action2)
