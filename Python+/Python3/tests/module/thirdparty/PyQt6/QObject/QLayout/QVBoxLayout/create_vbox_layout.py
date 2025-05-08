from PyQt6.QtWidgets import QVBoxLayout, QPushButton

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')

    layout: QVBoxLayout = QVBoxLayout()
    layout.addWidget(button1)
    layout.addWidget(button2)

    window.setLayout(layout)
