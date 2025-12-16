from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QPushButton, QVBoxLayout

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    icon: QIcon = QIcon('info.png')

    button: QPushButton = QPushButton()
    button.setIcon(icon)
    button.setFixedWidth(button.sizeHint().width())
    button.clicked.connect(lambda: print("Clicked"))

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
    window.resize(400, 300)
