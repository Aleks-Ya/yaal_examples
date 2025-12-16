from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QHBoxLayout, QTextEdit

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    text1: QTextEdit = QTextEdit()
    text1.setPlainText('Line 1\nLine 2\nLine 3')
    text1.setFixedSize(100, 200)

    text2: QTextEdit = QTextEdit()
    text2.setPlainText('Line 1\nLine 2')
    text2.setFixedSize(100, 150)

    text3: QTextEdit = QTextEdit()
    text3.setPlainText('Line 1')
    text3.setFixedSize(100, 100)

    layout: QHBoxLayout = QHBoxLayout()
    layout.setAlignment(Qt.AlignmentFlag.AlignRight)
    layout.addWidget(text1, alignment=Qt.AlignmentFlag.AlignBottom)
    layout.addWidget(text2, alignment=Qt.AlignmentFlag.AlignBottom)
    layout.addWidget(text3, alignment=Qt.AlignmentFlag.AlignTop)

    window.setLayout(layout)
    window.setFixedSize(600, 300)
