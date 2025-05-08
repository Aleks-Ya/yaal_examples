from PyQt6.QtWidgets import QLabel
from PyQt6.QtGui import QFont

from src.module.thirdparty.PyQt6 import app

with app():
    font: QFont = QFont('Arial', 40)
    label: QLabel = QLabel('Hello, PyQt6!')
    label.setFont(font)
    label.show()
