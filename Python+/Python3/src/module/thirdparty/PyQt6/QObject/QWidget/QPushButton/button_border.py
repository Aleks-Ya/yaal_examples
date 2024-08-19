from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Button without border')
    button.setStyleSheet("border: none;")
    button.show()
