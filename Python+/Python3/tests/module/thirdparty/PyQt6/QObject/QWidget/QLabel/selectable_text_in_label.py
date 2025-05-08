from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QLabel

from src.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('Select me!')
    label.setTextInteractionFlags(Qt.TextInteractionFlag.TextSelectableByMouse)
    label.show()
