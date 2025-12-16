from PyQt6.QtWidgets import QLineEdit

from tests.module.thirdparty.PyQt6 import app

with app():
    line_edit: QLineEdit = QLineEdit('Hello, PyQt6!')
    line_edit.show()
