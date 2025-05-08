from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QVBoxLayout, QPushButton

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1 long long long')
    button1.setFixedWidth(button1.sizeHint().width())

    button2: QPushButton = QPushButton('Button 2 medium')
    button2.setFixedWidth(button2.sizeHint().width())

    button3: QPushButton = QPushButton('Button 3')
    button3.setFixedWidth(button2.sizeHint().width())

    layout: QVBoxLayout = QVBoxLayout()
    layout.setAlignment(Qt.AlignmentFlag.AlignTop)
    layout.addWidget(button1)
    layout.addWidget(button2, alignment=Qt.AlignmentFlag.AlignCenter)
    layout.addWidget(button3, alignment=Qt.AlignmentFlag.AlignRight)

    window.setFixedSize(400, 500)
    window.setLayout(layout)
