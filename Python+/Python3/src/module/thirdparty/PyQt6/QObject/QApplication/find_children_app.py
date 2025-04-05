from PyQt6.QtCore import QObject
from PyQt6.QtWidgets import QApplication, QPushButton

from src.module.thirdparty.PyQt6 import app

with app():
    button: QPushButton = QPushButton('Quit application')
    button.clicked.connect(QApplication.quit)

    children: list[QObject] = QApplication.instance().children()
    print(children)

    button.show()
