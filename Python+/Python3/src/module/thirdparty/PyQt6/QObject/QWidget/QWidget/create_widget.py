from PyQt6.QtWidgets import QWidget

from src.module.thirdparty.PyQt6 import app

with app():
    widget: QWidget = QWidget()
    widget.show()
