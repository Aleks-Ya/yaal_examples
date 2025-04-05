from PyQt6.QtWidgets import QPushButton, QApplication

from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Quit application')
    button.clicked.connect(QApplication.quit)
    button.show()
