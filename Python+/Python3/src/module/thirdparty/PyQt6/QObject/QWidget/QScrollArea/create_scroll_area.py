from PyQt6.QtWidgets import QLabel, QScrollArea

from src.module.thirdparty.PyQt6 import app

with app():
    label: QLabel = QLabel('abc ' * 100)
    scroll_area: QScrollArea = QScrollArea()
    scroll_area.setWidget(label)
    scroll_area.show()
