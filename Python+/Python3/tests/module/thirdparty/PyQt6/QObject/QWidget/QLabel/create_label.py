from PyQt6.QtWidgets import QLabel

from tests.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('Hello, PyQt6!')
    label.show()
