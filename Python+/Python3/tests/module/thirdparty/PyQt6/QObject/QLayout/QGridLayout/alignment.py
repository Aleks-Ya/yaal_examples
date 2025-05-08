from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QGridLayout, QPushButton

from src.module.thirdparty.PyQt6 import window

with window() as window:
    button1: QPushButton = QPushButton('Button 1')
    button1.setFixedSize(200, 200)
    button2: QPushButton = QPushButton('Button 2')
    button2.setFixedSize(100, 100)
    button3: QPushButton = QPushButton('Button 3')
    button3.setFixedSize(100, 100)
    button4: QPushButton = QPushButton('Button 4')
    button4.setFixedSize(100, 100)
    button5: QPushButton = QPushButton('Button 5')
    button5.setFixedSize(100, 100)
    button6: QPushButton = QPushButton('Button 6')
    button6.setFixedSize(100, 100)
    button7: QPushButton = QPushButton('Button 7')
    button7.setFixedSize(100, 100)

    layout: QGridLayout = QGridLayout()
    layout.addWidget(button1, 0, 0)
    layout.addWidget(button2, 0, 1, Qt.AlignmentFlag.AlignTop)
    layout.addWidget(button3, 0, 2, Qt.AlignmentFlag.AlignVCenter)
    layout.addWidget(button4, 0, 3, Qt.AlignmentFlag.AlignBottom)
    layout.addWidget(button5, 1, 0, Qt.AlignmentFlag.AlignRight)
    layout.addWidget(button6, 2, 0, Qt.AlignmentFlag.AlignHCenter)
    layout.addWidget(button7, 3, 0, Qt.AlignmentFlag.AlignLeft)

    window.setLayout(layout)
