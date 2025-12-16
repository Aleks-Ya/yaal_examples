from PyQt6.QtGui import QFont
from PyQt6.QtWidgets import QLabel

from tests.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('Hello, PyQt6!')
    font: QFont = QFont("Arial", 24)
    font.setWeight(QFont.Weight.Bold)
    font.setItalic(True)
    label.setFont(font)
    label.show()
