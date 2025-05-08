from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import window

with window() as window:
    window.setGeometry(200, 100, 400, 300)
    label: QLabel = QLabel(window)
    label.setText('Hello, PyQt6!')
    label.move(50, 50)
