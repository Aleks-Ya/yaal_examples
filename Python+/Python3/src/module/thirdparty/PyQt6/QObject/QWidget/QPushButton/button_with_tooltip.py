from PyQt6.QtWidgets import QPushButton, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button: QPushButton = QPushButton('Hover over me')
    button.setToolTip("Good boy")

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
