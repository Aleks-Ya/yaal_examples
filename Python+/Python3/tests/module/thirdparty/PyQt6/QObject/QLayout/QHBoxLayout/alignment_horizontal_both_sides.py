from PyQt6.QtWidgets import QHBoxLayout, QTextEdit

from tests.module.thirdparty.PyQt6 import window

with window() as window:
    text1: QTextEdit = QTextEdit()
    text1.setPlainText('Line 1\nLine 2\nLine 3')
    text1.setFixedSize(100, 200)

    text2: QTextEdit = QTextEdit()
    text2.setPlainText('Line 1\nLine 2')
    text2.setFixedSize(100, 150)

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(text1)
    layout.addStretch()
    layout.addWidget(text2)

    window.setLayout(layout)
    window.setFixedSize(600, 300)
