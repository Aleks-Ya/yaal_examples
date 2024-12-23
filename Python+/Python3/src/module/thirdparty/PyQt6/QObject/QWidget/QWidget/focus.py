from PyQt6.QtWidgets import QPushButton

from src.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    button1: QPushButton = QPushButton('Button 1')
    button1.clicked.connect(lambda: print('Button 1 clicked'))
    button2: QPushButton = QPushButton('Button 2')
    button2.clicked.connect(lambda: print('Button 2 clicked'))

    layout.addWidget(button1)
    layout.addWidget(button2)

    button2.setFocus()
