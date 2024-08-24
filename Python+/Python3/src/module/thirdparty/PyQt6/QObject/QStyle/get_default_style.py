from PyQt6.QtWidgets import QApplication, QStyle

from src.module.thirdparty.PyQt6 import app

with app():
    style: QStyle = QApplication.style()
    print(style.objectName())
