from typing import Optional

from PyQt6.QtGui import QColor
from PyQt6.QtWidgets import QApplication, QPushButton, QColorDialog, QWidget, QVBoxLayout


def show_color_dialog():
    color: Optional[QColor] = QColorDialog.getColor()
    if color.isValid():
        window.setStyleSheet(f'QWidget {{ background-color: {color.name()} }}')


app: QApplication = QApplication([])

window: QWidget = QWidget()
layout: QVBoxLayout = QVBoxLayout()

button: QPushButton = QPushButton('Choose Color')
button.clicked.connect(show_color_dialog)
layout.addWidget(button)

window.setLayout(layout)
window.show()

app.exec()
