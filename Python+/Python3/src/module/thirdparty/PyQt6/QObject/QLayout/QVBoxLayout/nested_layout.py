from PyQt6.QtWidgets import QVBoxLayout, QPushButton, QHBoxLayout

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')
    button3: QPushButton = QPushButton('Button 3')
    button4: QPushButton = QPushButton('Button 4')

    nested_layout_1: QVBoxLayout = QVBoxLayout()
    nested_layout_1.addWidget(button1)
    nested_layout_1.addWidget(button2)

    nested_layout_2: QHBoxLayout = QHBoxLayout()
    nested_layout_2.addWidget(button3)
    nested_layout_2.addWidget(button4)

    outer_layout: QVBoxLayout = QVBoxLayout()
    outer_layout.addLayout(nested_layout_1)
    outer_layout.addLayout(nested_layout_2)

    window.setLayout(outer_layout)
