from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Click Me')
    button.setContentsMargins(50, 40, 30, 20)  # does not work
    button.setStyleSheet("""
    QPushButton {
        padding: 10px;
    }
    """)
    button.show()
