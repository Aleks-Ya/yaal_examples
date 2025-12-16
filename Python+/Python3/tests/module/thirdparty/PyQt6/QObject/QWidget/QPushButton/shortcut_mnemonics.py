from PyQt6.QtWidgets import QPushButton

from tests.module.thirdparty.PyQt6 import vbox

with vbox() as layout:
    button1: QPushButton = QPushButton('&Click Me')
    button2: QPushButton = QPushButton('Click &Me')
    layout.addWidget(button1)
    layout.addWidget(button2)
