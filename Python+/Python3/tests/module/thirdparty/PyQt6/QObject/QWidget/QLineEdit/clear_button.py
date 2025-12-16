from PyQt6.QtWidgets import QLineEdit

from tests.module.thirdparty.PyQt6 import app

with app():
    line_edit: QLineEdit = QLineEdit('Clear me')
    line_edit.setClearButtonEnabled(True)
    line_edit.show()
