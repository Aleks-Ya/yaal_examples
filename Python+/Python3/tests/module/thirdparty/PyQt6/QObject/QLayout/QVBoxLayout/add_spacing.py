from PyQt6.QtWidgets import QVBoxLayout, QTextEdit

from src.module.thirdparty.PyQt6 import window

with window() as window:
    text_edit_1: QTextEdit = QTextEdit('Text 1')
    text_edit_2: QTextEdit = QTextEdit('Text 2')

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(text_edit_1)
    layout.addSpacing(100)
    layout.addWidget(text_edit_2)

    window.setLayout(layout)
