from PyQt6.QtWidgets import QRadioButton

from tests.module.thirdparty.PyQt6 import app

with app():
    checkbox: QRadioButton = QRadioButton("Check me!")
    checkbox.show()
