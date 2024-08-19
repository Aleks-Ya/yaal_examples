from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QPushButton, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    icon: QIcon = QIcon('info.png')

    button: QPushButton = QPushButton()
    button.setIcon(icon)
    button.setIconSize(button.sizeHint())
    button.setFixedSize(icon.actualSize(button.iconSize()))

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
    window.resize(400, 300)
