from PyQt6.QtGui import QKeySequence
from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import app


def on_clicked():
    print("Button was clicked")


with app():
    key_sequence: QKeySequence = QKeySequence("Ctrl+Shift+H")
    button: QPushButton = QPushButton('Press Ctrl+Shift+H')
    button.setShortcut(key_sequence)
    button.clicked.connect(on_clicked)
    button.show()
