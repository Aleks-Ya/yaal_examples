from PyQt6.QtGui import QFont
from PyQt6.QtWidgets import QLabel

from tests.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('Hello, PyQt6!')
    font: QFont = label.font()
    font.setPointSize(int(font.pointSize() * 1.5))
    label.setFont(font)
    label.show()
