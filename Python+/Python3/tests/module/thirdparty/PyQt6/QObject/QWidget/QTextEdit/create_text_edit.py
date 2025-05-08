from PyQt6.QtWidgets import QTextEdit

from src.module.thirdparty.PyQt6 import app

with app():
    text_edit: QTextEdit = QTextEdit()
    text_edit.show()
