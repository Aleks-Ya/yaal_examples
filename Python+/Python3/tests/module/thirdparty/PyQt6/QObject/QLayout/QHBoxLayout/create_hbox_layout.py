from PyQt6.QtWidgets import QPushButton, QHBoxLayout

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')
    assert button1.parent() is None

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(button1)
    layout.addWidget(button2)

    window.setLayout(layout)
    assert button1.parent() == window
