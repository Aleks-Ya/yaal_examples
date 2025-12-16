from PyQt6.QtWidgets import QPushButton, QApplication

from tests.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Quit application')
    button.clicked.connect(QApplication.quit)
    button.show()
