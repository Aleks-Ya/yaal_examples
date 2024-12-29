from PyQt6.QtGui import QKeySequence, QShortcut
from PyQt6.QtWidgets import QPushButton, QApplication, QWidget

from src.module.thirdparty.PyQt6 import app


def __get_all_shortcuts(application: QApplication) -> list[QShortcut]:
    shortcuts: list[QShortcut] = []
    widgets: list[QWidget] = application.topLevelWidgets()
    for widget in widgets:
        shortcuts.extend(widget.findChildren(QShortcut))
    return shortcuts


# NOT WORK
with app() as app:
    key_sequence: QKeySequence = QKeySequence("Ctrl+Shift+H")
    button: QPushButton = QPushButton('Press Ctrl+Shift+H')
    button.clicked.connect(lambda: print("Button was clicked"))
    button.show()

    all_shortcuts: list[QShortcut] = __get_all_shortcuts(app)
    print(all_shortcuts)
