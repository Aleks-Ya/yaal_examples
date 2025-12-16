from PyQt6.QtWidgets import QPushButton, QWidget, QLineEdit

from tests.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    layout.parent().setStyleSheet("*:focus { border: 2px solid blue; }")

    line_edit_1: QLineEdit = QLineEdit('Line Edit 1')
    line_edit_2: QLineEdit = QLineEdit('Line Edit 2')
    line_edit_3: QLineEdit = QLineEdit('Line Edit 3')
    line_edit_4: QLineEdit = QLineEdit('Line Edit 4')

    layout.addWidget(line_edit_1)
    layout.addWidget(line_edit_3)
    layout.addWidget(line_edit_4)


    def add_more_line_edits():
        layout.insertWidget(1, line_edit_2)
        QWidget.setTabOrder(line_edit_1, line_edit_2)
        QWidget.setTabOrder(line_edit_2, line_edit_3)
        QWidget.setTabOrder(line_edit_3, line_edit_4)


    add_more_line_edits_button: QPushButton = QPushButton('Add 2nd Line Edit')
    add_more_line_edits_button.clicked.connect(add_more_line_edits)
    layout.addWidget(add_more_line_edits_button)
