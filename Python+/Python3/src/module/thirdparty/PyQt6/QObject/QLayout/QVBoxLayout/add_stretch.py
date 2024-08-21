from PyQt6.QtWidgets import QVBoxLayout, QTextEdit

from src.module.thirdparty.PyQt6 import window

with window() as window:
    text_edit_1: QTextEdit = QTextEdit('Text 1')
    text_edit_2: QTextEdit = QTextEdit('Text 2')
    text_edit_3: QTextEdit = QTextEdit('Text 3')

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(text_edit_1)
    layout.addStretch()
    layout.addWidget(text_edit_2)
    layout.addStretch()
    layout.addWidget(text_edit_3)

    window.setLayout(layout)
