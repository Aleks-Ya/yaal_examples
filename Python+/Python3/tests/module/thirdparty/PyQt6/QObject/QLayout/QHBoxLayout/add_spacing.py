from PyQt6.QtWidgets import QHBoxLayout, QTextEdit

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    text_edit_1: QTextEdit = QTextEdit('Text 1')
    text_edit_2: QTextEdit = QTextEdit('Text 2')

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(text_edit_1)
    layout.addSpacing(100)
    layout.addWidget(text_edit_2)

    window.setLayout(layout)
