from typing import Optional

from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QPushButton, QColorDialog, QVBoxLayout

from src.module.thirdparty.PyQt6 import window


def show_color_dialog():
    color: Optional[QColor] = QColorDialog.getColor()
    if color.isValid():
        window.setStyleSheet(f'QWidget {{ background-color: {color.name()} }}')


with window() as window:
    button: QPushButton = QPushButton('Choose Color')
    button.clicked.connect(show_color_dialog)

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button)

    window.setLayout(layout)
