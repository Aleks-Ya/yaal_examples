# Set layout for a widget via constructor
from PyQt6.QtWidgets import QPushButton, QHBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')
    assert button1.parent() is None

    layout: QHBoxLayout = QHBoxLayout(window)
    layout.addWidget(button1)
    layout.addWidget(button2)
    assert button1.parent() == window
