from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Click Me')
    button.show()
