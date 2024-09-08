from PyQt6.QtCore import QSize, QMargins
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import QPushButton, QVBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    icon: QIcon = QIcon('info.png')

    button: QPushButton = QPushButton()
    size: int = 50
    margin: int = 10
    button.setFixedSize(size, size)
    icon_size: QSize = button.size().shrunkBy(QMargins(margin, margin, margin, margin))
    button.setIcon(icon)
    button.setIconSize(icon_size)
    # noinspection PyUnresolvedReferences
    button.clicked.connect(lambda: print("Clicked"))

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
    window.resize(400, 300)
