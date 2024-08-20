from PyQt6.QtWidgets import QGridLayout, QTextEdit

from src.module.thirdparty.PyQt6 import window

with window() as window:
    text00: QTextEdit = QTextEdit('Text 00 (column span)')
    text10: QTextEdit = QTextEdit('Text 10')
    text11: QTextEdit = QTextEdit('Text 11 (row span)')
    text12: QTextEdit = QTextEdit('Text 12')
    text20: QTextEdit = QTextEdit('Text 20')
    text22: QTextEdit = QTextEdit('Text 22')

    layout: QGridLayout = QGridLayout()
    layout.addWidget(text00, 0, 0, 1, 3)
    layout.addWidget(text10, 1, 0)
    layout.addWidget(text11, 1, 1, 2, 1)
    layout.addWidget(text12, 1, 2)
    layout.addWidget(text20, 2, 0)
    layout.addWidget(text22, 2, 2)

    window.setLayout(layout)
