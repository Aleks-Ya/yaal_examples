from PyQt6.QtWidgets import QPushButton, QGridLayout, QLayoutItem

from tests.module.thirdparty.PyQt6 import app

with app():
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')
    button3: QPushButton = QPushButton('Button 3')

    layout: QGridLayout = QGridLayout()
    layout.addWidget(button1, 0, 0)
    layout.addWidget(button2, 0, 1)
    layout.addWidget(button3, 1, 0)

    item00: QLayoutItem = layout.itemAtPosition(0, 0)
    item01: QLayoutItem = layout.itemAtPosition(0, 1)
    item10: QLayoutItem = layout.itemAtPosition(1, 0)

    assert item00.widget() == button1
    assert item01.widget() == button2
    assert item10.widget() == button3
