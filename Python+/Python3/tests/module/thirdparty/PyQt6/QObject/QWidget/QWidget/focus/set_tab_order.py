from PyQt6.QtWidgets import QPushButton, QWidget

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    layout.parent().setStyleSheet("QPushButton:focus { border: 2px solid blue; }")

    button1: QPushButton = QPushButton('Button 1')
    button1.clicked.connect(lambda: print('Button 1 clicked'))

    button2: QPushButton = QPushButton('Button 2')
    button2.clicked.connect(lambda: print('Button 2 clicked'))

    button3: QPushButton = QPushButton('Button 3')
    button3.clicked.connect(lambda: print('Button 3 clicked'))

    layout.addWidget(button1)
    layout.addWidget(button2)
    layout.addWidget(button3)

    QWidget.setTabOrder(button2, button1)
    QWidget.setTabOrder(button1, button3)
    QWidget.setTabOrder(button3, button2)
