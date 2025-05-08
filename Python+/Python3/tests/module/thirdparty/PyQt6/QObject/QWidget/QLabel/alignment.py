from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('Hello, PyQt6!')
    label.setFixedSize(400, 300)
    label.setAlignment(Qt.AlignmentFlag.AlignCenter)
    label.show()
