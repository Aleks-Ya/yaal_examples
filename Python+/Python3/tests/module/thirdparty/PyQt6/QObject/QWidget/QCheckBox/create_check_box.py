from PyQt6.QtWidgets import QCheckBox

from src.module.thirdparty.PyQt6 import app

with app():
    checkbox: QCheckBox = QCheckBox("Check me!")
    checkbox.show()
