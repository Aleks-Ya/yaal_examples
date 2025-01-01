from PyQt6.QtWidgets import QLineEdit

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    line_edit_1: QLineEdit = QLineEdit('Line Edit 1')
    line_edit_2: QLineEdit = QLineEdit('Line Edit 2')
    line_edit_3: QLineEdit = QLineEdit('Line Edit 3')
    line_edit_4: QLineEdit = QLineEdit('Line Edit 4')

    line_edit_2.setFocusProxy(line_edit_4)

    layout.addWidget(line_edit_1)
    layout.addWidget(line_edit_2)
    layout.addWidget(line_edit_3)
    layout.addWidget(line_edit_4)
