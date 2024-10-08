from PyQt6.QtWidgets import QLineEdit

from src.module.thirdparty.PyQt6 import app

with app():
    line_edit: QLineEdit = QLineEdit()
    line_edit.setPlaceholderText("Enter here your text")
    line_edit.show()
