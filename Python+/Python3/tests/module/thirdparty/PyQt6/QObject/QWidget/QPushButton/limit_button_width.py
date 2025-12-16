from PyQt6.QtWidgets import QPushButton, QHBoxLayout

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    button: QPushButton = QPushButton('Click Me')
    button.setFixedWidth(button.sizeHint().width())

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
    window.setFixedWidth(1000)
