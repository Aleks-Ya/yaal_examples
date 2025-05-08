from PyQt6.QtGui import QKeySequence, QShortcut

from src.module.thirdparty.PyQt6 import main_window


def on_hotkey():
    print("Hotkey was activated")


with main_window() as main_window:
    key_sequence: QKeySequence = QKeySequence("Ctrl+Shift+H")
    shortcut: QShortcut = QShortcut(key_sequence, main_window)
    shortcut.activated.connect(on_hotkey)
