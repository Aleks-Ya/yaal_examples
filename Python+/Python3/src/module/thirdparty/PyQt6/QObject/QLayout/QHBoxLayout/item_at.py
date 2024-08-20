from PyQt6.QtWidgets import QPushButton, QHBoxLayout, QLayoutItem

from src.module.thirdparty.PyQt6 import app

with app():
    button1: QPushButton = QPushButton('Button 1')
    button2: QPushButton = QPushButton('Button 2')

    layout: QHBoxLayout = QHBoxLayout()
    layout.addWidget(button1)
    layout.addWidget(button2)

    item0: QLayoutItem = layout.itemAt(0)
    item1: QLayoutItem = layout.itemAt(1)

    assert item0.widget() == button1
    assert item1.widget() == button2
