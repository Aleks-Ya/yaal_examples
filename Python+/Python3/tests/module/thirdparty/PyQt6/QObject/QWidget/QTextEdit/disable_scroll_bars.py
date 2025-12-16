from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QTextEdit

from tests.module.thirdparty.PyQt6 import app

with app():
    text_edit: QTextEdit = QTextEdit()
    text_edit.setText("abc " * 400)
    text_edit.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
    text_edit.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
    text_edit.show()
